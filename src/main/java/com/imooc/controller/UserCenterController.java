package com.imooc.controller;

import com.imooc.entity.OrderList;
import com.imooc.entity.ShoppingCart;
import com.imooc.service.AdminUserService;
import com.imooc.service.AdminUserShoppingCartService;
import com.imooc.service.ShippingAddressService;
import com.imooc.service.UserCenterService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
/**
 * 这个模块主要是用来实现当用户登录成功之后结算商品修改自己账号密码的
 * 一系列的操作
 */
@Controller
@RequestMapping("/user")
public class UserCenterController {
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private ShippingAddressService shippingAddressService;
    @Autowired
    private AdminUserShoppingCartService adminUserShoppingCartService;

    //定义一个用于存放这个用户每件商品总金额的数组
    List<Integer>list = new ArrayList<Integer>();
    /**
     * 这个主要是用来实现前端页面跳转用的，跳转到个人用户中心用的
     */
    @RequestMapping(method = RequestMethod.GET,value = "/center")
    public void userCenter(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/userCenter.html");
        InputStream in = resource.getInputStream();
        // 创建输出流
        OutputStream out = response.getOutputStream();
        // 缓存区
        byte buffer[] = new byte[1024];
        int len = 0;
        // 循环将输入流中的内容读取到缓冲区中
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        // 关闭
        in.close();
        out.close();
    }
    /**
     *这个主要是用来实现从个人中心的"修改个人信息"跳转到"修改密码这个功能上的"
     */
    @RequestMapping(method = RequestMethod.GET,value = "/accountManagement")
    public void userInformation(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/accountManagement.html");
        InputStream in = resource.getInputStream();
        // 创建输出流
        OutputStream out = response.getOutputStream();
        // 缓存区
        byte buffer[] = new byte[1024];
        int len = 0;
        // 循环将输入流中的内容读取到缓冲区中
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        // 关闭
        in.close();
        out.close();

    }
    /**
     * 这个主要是实现修改密码功能的
     */
    @RequestMapping(method = RequestMethod.POST,value = "/accountManagement")
    @ResponseBody
    public Map<String,String> changePassword(
            @RequestParam Map<String,String> info,
            HttpServletResponse response,
            HttpServletRequest request,
            HttpSession session
            ){
        Object name =info.get("oldPassword");
        String oldPassword = name.toString();
        Object name1 =info.get("newPassword1");
        String passWord = name1.toString();
        Object name2 =info.get("newPassword2");
        String newPassword2 = name2.toString();
        if (oldPassword.length() != 0 && passWord.length() != 0 && newPassword2.length() != 0){
            //登录成功之后通过session来获取账号
            Object attribute = request.getSession().getAttribute("userName");
            String userName = attribute.toString();
            String a = adminUserService.find(userName).toString().substring(9);
            //将获取到的账号和密码发往后端的数据库中进行比对
            //将获取到的数据先转换成为jsonobject
            JSONObject b = JSONObject.fromObject(a);
            //获取json中的密码
            String name4 = b.getString("passWord");
            //将获取到的密码与前端传入的密码进行比对
            if (oldPassword.equals(name4)){
                //如果前后端密码一致执行下面的代码
                //先比对两次输入的新密码是否一致
                   if(name1.equals(name2)){
                       //如果两次输入的新密码一致就更改后台数据库中的密码
                       System.out.println("开始修改密码");
                       userCenterService.changePassword(userName,passWord);
                       info.put("resultCode","200");
                        return info;
                   }else{
                       //如果不一致执行以下的代码
                   }
            }else{
                System.out.println("false");
            }
        }
       return null;
    }

    /**
     * 这个功能用来实现购物车的查询商品信息的功能的
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST,value = "/checkOutTheShoppingCart")
    @ResponseBody
    public Map<String,String> checkOutTheShoppingCart(
            @RequestParam Map<String,String> info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        String userName = request.getSession().getAttribute("userName").toString();
        List<ShoppingCart>adminUser = shippingAddressService.lookForTheAddress(userName);
        /*这个查询出来的结果是
        [ShoppingCart{userName='123456', address='四川省资阳市台阳路石油小区', phoneNumber='17883464189', nickname='苏涛'}]
        需要将它转换为JSONArray然后再转换为JSONObject*/
        JSONArray json = JSONArray.fromObject(adminUser);
        String test = json.getString(0);
        /*转换成为JSONArrray的结果
        {"address":"四川省资阳市台阳路石油小区","phoneNumber":"17883464189","nickname":"苏涛","userName":"123456"}
        */
        JSONObject test1 = JSONObject.fromObject(test);
        String address = test1.getString("address");
        String phoneNumber = test1.getString("phoneNumber");
        String nickName = test1.getString("nickname");
        info.put("nickname",nickName);
        info.put("address",address);
        info.put("phoneNumber",phoneNumber);
        return info;
    }

    /**
     * 这个是用来将数据库中的图片等信息可以展示在前端页面上的
     * （思考了一天暂时没有好的饭没有方法去做，后面再来，先注释掉留着）
     * @param info
     * @param response
     * @param request
     * @param session
     * @return
     */
    /*@RequestMapping(method = RequestMethod.POST,value = "/settlementProductList")
    @ResponseBody
    public Map<String,String> settlementProductList(
            @RequestParam Map<String,String> info,
            HttpServletResponse response,
            HttpServletRequest request,
            HttpSession session
    ){
    String userName = request.getSession().getAttribute("userName").toString();
    List<OrderList>img = adminUserShoppingCartService.findimg(userName);
    String a = img.toString();
    JSONArray imgdata = JSONArray.fromObject(img);
    *//*int i = imgdata.size();*//*
        for (int i =0;i<imgdata.size();i++){
            String a1 =Integer.toString(i);
            String test = imgdata.getString(i);
            JSONObject test1 = JSONObject.fromObject(test);
            String productName = test1.getString("productName");
            String quantity = test1.getString("quantity");
            String price = test1.getString("price");
            info.put("productName"+i,productName);
            info.put("quantity"+i,quantity);
            info.put("price"+i,price);
            info.put("information"+i,a1);
        }
        System.out.println("数据"+info);
        return info;
    }*/

    /**
     * 这个是用来修改用户地址的
     * @param info
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST,value = "/modifyShippingAddress")
    @ResponseBody
    public Map<String,String> modifyShippingAddress(
            @RequestParam Map<String,String> info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
            String userName = request.getSession().getAttribute("userName").toString();
            Object shippingAddress = info.get("shippingAddress");
            //收件地址
            String shippingAddress1 = shippingAddress.toString();
            Object recipient = info.get("recipient");
            //收件人姓名
            String nickName = recipient.toString();
            Object phoneNumber = info.get("phoneNumber1");
            String phoneNumber1 = phoneNumber.toString();
            int change = shippingAddressService.changeDeliveryInformation(userName,shippingAddress1,nickName,phoneNumber1);
            if (change == 1){
                info.put("resultCode","200");
                return info;
            }else{
                info.put("resultCode","500");
                return info;
            }

    }

    /**
     * 用来计算用户在支付宝二维码那个界面的支付金额的问题
     * @param info
     * @param response
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST,value = "/paymentAmount")
    @ResponseBody
    public Map<String,String> paymentAmount(
            @RequestParam Map<String,String> info,
            HttpServletResponse response,
            HttpServletRequest request,
            HttpSession session
    )throws Exception{
        String userName = request.getSession().getAttribute("userName").toString();
        List<OrderList>totalAmount = adminUserShoppingCartService.totalAmount(userName);
        String totalAmount1 = totalAmount.toString();
        System.out.println("商品的价格是"+totalAmount1);
        JSONArray change1 = JSONArray.fromObject(totalAmount);
        //将获取到的数据添加到数组中去
        for (int i = 0;i<change1.size();i++){
            String change2 = change1.getString(i);
            JSONObject change3 = JSONObject.fromObject(change2);
            int price = Integer.parseInt(change3.getString("price"));
            int quantity = Integer.parseInt(change3.getString("quantity"));
            //商品的总额度
            int totalAmount2 = price*quantity;
            list.add(totalAmount2);
        }
        int sum=0;
        //将数组中的数据取出来然后进行累加得到该用户购物车中商品的总金额
        for (int i=0;i<list.size();i++){
            sum = sum+list.get(i);
        }
        //将获取到的总金额推送到前端去展示
        info.put("realTotalAmount",Integer.toString(sum));
        list.clear();
        return info;
    }
}
