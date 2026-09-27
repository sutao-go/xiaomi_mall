package com.imooc.controller;

import com.imooc.common.BizException;
import com.imooc.common.Result;
import com.imooc.common.ResultCode;
import com.imooc.common.SessionUtil;
import com.imooc.entity.OrderList;
import com.imooc.service.AdminUserShoppingCartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台购物：商品页跳转、加入购物车、购物车列表、修改数量
 * <p>
 * 重构要点（原文件 80KB / 1724 行）：
 * 1. 21 个商品的“加购物车”方法逻辑完全相同（商品名与价格由前端传入），
 *    合并为一个方法映射全部 URL，代码量减少约 90%，前端 URL 零改动
 * 2. 删除 Controller 可变成员变量 consumer/map/list/i/jsonAddList
 *    （原为单例字段，并发下会串号，A 用户可能操作到 B 用户的购物车）
 * 3. 加购物车改为一条原子 upsert SQL，修掉“先查后写”的并发丢更新
 * 4. 价格用 BigDecimal 校验（原 String -> int 解析，无小数且 NULL 即崩）
 * 5. 分页改为真实 count + LIMIT（原 totalCount 恒为 1，且 index 算了不用）
 * 6. 未登录由 SessionUtil 统一抛 401，不再 user.toString() 直接 NPE
 *
 * @author sutao
 */
@Controller
@RequestMapping(value = "/orderlist")
public class OrderListController {

    /** 商品页：URL 片段 -> 静态页面文件名 */
    private static final Map<String, String> PRODUCT_PAGES;

    /** 加入购物车的 URL（与原 21 个方法一一对应，保持前端兼容） */
    private static final String[] ADD_CART_URLS = {
            "/xiaomi10pro", "/RedmiK30", "/blackShark", "/redmi9A", "/xiaomi10youth",
            "/xiaomi10", "/redMiK30pro", "/RedmiK30ProZoomVersion", "/RedmiSmartTVX65",
            "/RedmiSmartTVX70", "/fullScreenTVE55A", "/miFullScreenTVE32C",
            "/mijiaAirConditioning", "/miTV4A60inches", "/mijiaWashingMachine",
            "/redMiWashingMachine", "/computer1", "/computer2", "/mixAlpha",
            "/redmi10XPro", "/miBand4"
    };

    static {
        Map<String, String> pages = new HashMap<>();
        pages.put("xiaomi10pro", "xiaomi_10_pro.html");
        pages.put("RedmiK30", "redMi_k30.html");
        pages.put("blackShark", "blackShark.html");
        pages.put("redmi9A", "Redmi9A.html");
        pages.put("xiaomi10youth", "Mi10YouthEdition5G.html");
        pages.put("xiaomi10", "xiaomi_10.html");
        pages.put("redMiK30pro", "RedmiK30Pro.html");
        pages.put("RedmiK30ProZoomVersion", "RedmiK30ProZoomVersion.html");
        pages.put("RedmiSmartTVX65", "RedmiSmartTVX65.html");
        pages.put("RedmiSmartTVX70", "RedmiTV70inches.html");
        pages.put("fullScreenTVE55A", "FullScreenTVE55A.html");
        pages.put("miFullScreenTVE32C", "MiFullScreenTVE32C.html");
        pages.put("mijiaAirConditioning", "MijiaAirConditioning.html");
        pages.put("miTV4A60inches", "MiTV4A60inches.html");
        pages.put("mijiaWashingMachine", "mijiaWashingMachine.html");
        pages.put("redMiWashingMachine", "redMiWashingMachine.html");
        pages.put("computer1", "computer1.html");
        pages.put("computer2", "computer2.html");
        pages.put("mixAlpha", "MIXAlpha.html");
        pages.put("redmi10XPro", "Redmi10XPro5G.html");
        pages.put("miBand4", "MiBand4.html");
        PRODUCT_PAGES = Collections.unmodifiableMap(pages);
    }

    @Autowired
    private AdminUserShoppingCartService cartService;

    // ==================== 页面跳转 ====================

    /**
     * 全部商品页跳转（原 21 个重复方法，各自手工拷贝文件流）
     */
    @GetMapping(ADD_CART_URLS)
    public String productPage(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String key = uri.substring(uri.lastIndexOf('/') + 1);
        String page = PRODUCT_PAGES.get(key);
        if (page == null) {
            return "redirect:/templates/frontPage/index.html";
        }
        return "redirect:/templates/frontPage/CollectionOfProductPages/" + page;
    }

    @GetMapping("/orderInformation")
    public String orderInformation() {
        return "redirect:/templates/frontPage/orderList.html";
    }

    @GetMapping("/QueryProductInformation")
    public String shoppingCartPage() {
        return "redirect:/templates/frontPage/ShoppingCart.html";
    }

    @GetMapping("/confirmOrder")
    public String confirmOrder() {
        return "redirect:/templates/frontPage/confirmOrder.html";
    }

    @GetMapping("/alipay")
    public String alipay() {
        return "redirect:/templates/frontPage/alipay.html";
    }

    // ==================== 加入购物车 ====================

    /**
     * 加入购物车：一个方法覆盖全部 21 个商品 URL
     */
    @PostMapping(ADD_CART_URLS)
    @ResponseBody
    public Result<Void> addToCart(@RequestParam Map<String, String> info, HttpSession session) {
        // 未登录直接抛 401（原实现未判空，必然 NPE）
        String consumer = SessionUtil.requireUserName(session);

        String productName = info.get("phoneName");
        String price = info.get("price");
        if (StringUtils.isBlank(productName) || StringUtils.isBlank(price)) {
            throw new BizException(ResultCode.PARAM_ERROR, "商品信息不完整");
        }
        BigDecimal priceValue = parsePrice(price);
        int quantity = parseQuantity(info.get("quantity"), 1);

        // 原子 upsert：有记录则数量累加，无记录则插入（修掉并发丢更新）
        cartService.addToCart(consumer, productName.trim(), priceValue.toPlainString(), quantity);
        return Result.ok();
    }

    // ==================== 购物车列表 ====================

    /**
     * 购物车列表（jqGrid 格式：{data:{totalCount,pageSize,totalPage,list}}）
     */
    @GetMapping("/QueryProductInformation/list")
    @ResponseBody
    public Result<Map<String, Object>> cartList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int rows,
            HttpSession session) {
        String user = SessionUtil.requireUserName(session);

        if (page < 1) {
            page = 1;
        }
        if (rows < 1) {
            rows = 10;
        }
        int totalCount = cartService.countCart(user);
        int totalPage = (int) Math.ceil((double) totalCount / rows);
        int offset = (page - 1) * rows;

        List<OrderList> list = cartService.queryProductInformationPage(user, offset, rows);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("totalCount", totalCount);
        data.put("pageSize", rows);
        data.put("totalPage", totalPage);
        data.put("list", list);
        return Result.ok(data);
    }

    // ==================== 修改数量 ====================

    /**
     * 修改购物车商品数量
     * <p>
     * 优先使用 productName（前端改造后传）；未传则回退到旧的 id（行号）定位，
     * 并加上边界校验（原实现可越界或取到 -1）。
     */
    @PostMapping("/modifiedProductQuantity")
    @ResponseBody
    public Result<Void> modifiedProductQuantity(@RequestParam Map<String, String> info, HttpSession session) {
        String userName = SessionUtil.requireUserName(session);

        String quantityStr = info.get("quantity");
        if (StringUtils.isBlank(quantityStr)) {
            throw new BizException(ResultCode.PARAM_ERROR, "数量不能为空");
        }
        int quantity = parseQuantity(quantityStr, -1);
        if (quantity < 1) {
            throw new BizException(ResultCode.PARAM_ERROR, "数量至少为 1");
        }

        String productName = info.get("productName");
        if (StringUtils.isBlank(productName)) {
            // 兼容旧前端：按行号定位
            String idStr = info.get("id");
            if (StringUtils.isBlank(idStr)) {
                throw new BizException(ResultCode.PARAM_ERROR, "缺少商品标识");
            }
            int id = parseQuantity(idStr, -1);
            List<OrderList> cart = cartService.queryProductInformation(userName);
            if (id < 1 || id > cart.size()) {
                throw new BizException(ResultCode.PARAM_ERROR, "商品不存在");
            }
            productName = cart.get(id - 1).getProductName();
        }

        cartService.updateQuantity(String.valueOf(quantity), userName, productName);
        return Result.ok();
    }

    // ==================== 工具方法 ====================

    private BigDecimal parsePrice(String price) {
        try {
            BigDecimal value = new BigDecimal(price.trim());
            if (value.compareTo(BigDecimal.ZERO) < 0) {
                throw new BizException(ResultCode.PARAM_ERROR, "商品价格不能为负数");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new BizException(ResultCode.PARAM_ERROR, "商品价格格式不正确");
        }
    }

    private int parseQuantity(String quantity, int defaultValue) {
        if (StringUtils.isBlank(quantity)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(quantity.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
