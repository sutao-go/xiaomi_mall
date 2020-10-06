package com.imooc.controller;

import com.imooc.service.AdminUserShoppingCartService;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/orderlist")
public class OrderListController {
    String consumer;
    @Autowired
    AdminUserShoppingCartService adminUserShoppingCartService;
    /**
     * 这个主要是用来跳转到带支付订单信息的页面的
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/orderInformation")
    public String orderInformation(){
        return "templates/frontPage/orderList";
    }

    /**
     *
     * @param response
     * @param request
     * @return
     * @throws Exception
     * 这个主要是用来跳转到订单支付页面然后做一系列的操作
     */
    @RequestMapping(method = RequestMethod.POST,value = "/orderInformation")
    public String getOrderInformation(
            HttpResponse response,
            HttpRequest request
    )throws Exception{
        return null;
    }

    /**
     * 这个主要是用来实现小米10pro网页跳转的
     */
    @RequestMapping(method = RequestMethod.GET,value = "/xiaomi10pro")
    public String xiaomi10(){
        return "templates/frontPage/CollectionOfProductPages/xiaomi_10_pro";
    }
    /**
     * 用于小米10pro业务处理的
     */
    @RequestMapping(method = RequestMethod.POST,value = "/xiaomi10pro")
    @ResponseBody
    public Map<String,String> doPostXiaomi10(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String quantity = adminUserShoppingCartService.checkProductQuantity(consumer);
                System.out.println("该用户购买的数量是"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity2 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity3 = quantity2+1;
                //将+1的数据写入数据库存储
                adminUserShoppingCartService.addQuantity(quantity3,consumer);
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个主要是用来实现Redmi K30 至尊纪念版网页跳转的
     */
    @RequestMapping(method = RequestMethod.GET,value = "/RedmiK30")
    public String RedmiK30() {
        return "templates/frontPage/CollectionOfProductPages/redMi_k30";
    }
    /**
     * 这个实现黑鲨手机网页跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/blackShark")
    public String blackShark(){
        return "templates/frontPage/CollectionOfProductPages/blackShark";
    }
    /**
     * 这个实现了Redmi 9A手机页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/redmi9A")
    public String redmi9A(){
        return "templates/frontPage/CollectionOfProductPages/Redmi9A";
    }
    /**
     * 这个实现了小米10青春版的手机页面跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/xiaomi10youth")
    public String xiaomi10youth(){
        return "templates/frontPage/CollectionOfProductPages/Mi10YouthEdition5G";
    }
    /**
     * 这个实现了小米10页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/xiaomi10")
    public String xiaomi10r(){
        return "templates/frontPage/CollectionOfProductPages/xiaomi_10";
    }
    /**
     * 这个实现了redmik30页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/redMiK30pro")
    public String redmik30(){
    return "templates/frontPage/CollectionOfProductPages/RedmiK30Pro";
    }
    /**
     * 这个实现了RedmiK30ProZoomVersion页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/RedmiK30ProZoomVersion")
    public String RedmiK30ProZoomVersion(){
        return "templates/frontPage/CollectionOfProductPages/RedmiK30ProZoomVersion";
    }
    /**
     * 这个实现了RedmiSmartTVX65页面的跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/RedmiSmartTVX65")
    public String RedmiSmartTVX65(){
        return "templates/frontPage/CollectionOfProductPages/RedmiSmartTVX65";
    }

    @RequestMapping(method = RequestMethod.GET,value = "/RedmiSmartTVX70")
    public String RedmiSmartTVX70(){
        return "templates/frontPage/CollectionOfProductPages/RedmiTV70inches";
    }
    /**
     * 这个实现了fullScreenTVE55A页面的跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/fullScreenTVE55A")
    public String fullScreenTVE55A(){
        return "templates/frontPage/CollectionOfProductPages/FullScreenTVE55A";
    }

    /**
     * 这个实现了MiFullScreenTVE32C页面的跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/miFullScreenTVE32C")
    public String miFullScreenTVE32C(){
        return "templates/frontPage/CollectionOfProductPages/MiFullScreenTVE32C";
    }
    /**
     *这个实现了MijiaAirConditioning页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/mijiaAirConditioning")
    public String mijiaAirConditioning(){
        return "templates/frontPage/CollectionOfProductPages/MijiaAirConditioning";
    }
    /**
     * 这个实现了小米电视4A 60英寸页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/miTV4A60inches")
    public String miTV4A60inches(){
        return "templates/frontPage/CollectionOfProductPages/MiTV4A60inches";
    }
    /**
     * 这个实现了米家互联网洗烘一体机Pro 10kg的页面跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/mijiaWashingMachine")
    public String mijiaWashingMachin(){
        return "templates/frontPage/CollectionOfProductPages/mijiaWashingMachine";
    }
    /**
     * 这个实现了Redmi全自动波轮洗衣机1A 8kg的页面跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/redMiWashingMachine")
    public String redmiWashingMachine(){
        return "templates/frontPage/CollectionOfProductPages/redMiWashingMachine";
    }

    /**
     * 这个实现了小米电脑页面的页面跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/computer1")
    public String computer1(){
        return "templates/frontPage/CollectionOfProductPages/computer1";
    }

    /**
     * 这个实现了小米电脑air的跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/computer2")
    public String computer2(){
        return "templates/frontPage/CollectionOfProductPages/computer2";
    }
    /**
     * 这个实现了MIXAlpha手机页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/mixAlpha")
    public String mixAlpha(){
        return "templates/frontPage/CollectionOfProductPages/MIXAlpha";
    }
    /**
     * 这个实现了Redmi10XPro手机页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/redmi10XPro")
    public String redmi10XPro(){
        return "templates/frontPage/CollectionOfProductPages/Redmi10XPro5G";
    }
    /**
     * 这个实现了小米手环4的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/miBand4")
    public String miBand4(){
        return "templates/frontPage/CollectionOfProductPages/MiBand4";
    }

    /**
     * 这个是用来实现结算购物车功能的
     * @return
     * 1.先查出这个属于用户所有的商品信息
     * 2.将所有的商品信息展示在前端
     * 3.当用户在点击结算按钮的时候结算前端页面购物车中所有商品的价格
     */
    @RequestMapping(method = RequestMethod.GET,value = "/QueryProductInformation")
    public String shoppingCart(){
        return "templates/frontPage/ShoppingCart";
    }

    @RequestMapping(method = RequestMethod.POST,value = "/QueryProductInformation")
    @ResponseBody
    public Map<String,String> shoppingCart1(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        String user = (String) request.getSession().getAttribute("userName");
        System.out.println("XXXX"+user);
        String text = adminUserShoppingCartService.queryProductInformation(user);
        System.out.println("可以查到数据了"+text);
        return info;
    }

}

