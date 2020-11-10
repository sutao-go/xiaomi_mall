package com.imooc.controller;

import com.imooc.entity.OrderList;
import com.imooc.service.AdminUserShoppingCartService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这个模块主要是用来处理前端用户的操作的
 * 比如像：用户购买商品等等的操作
 */
@Controller

@RequestMapping(value = "/orderlist")
public class OrderListController {
    //消费者
    String consumer;
    //用来装购物车中的数据
    Map map = new HashMap();
    //将上面map中存取的数据存到list中去
    List<String> list = new ArrayList<String>();
    //这个是用来存储从数据库中读取出来的用户条数的
    int i;
    String jsonAddList;
    @Autowired
    AdminUserShoppingCartService adminUserShoppingCartService;
    /**
     * 这个主要是用来跳转到带支付订单信息的页面的
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/orderInformation")
    public void orderInformation(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/orderList.html");
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
     * @param response
     * @param request
     * @return
     * @throws Exception
     * 这个主要是用来跳转到订单支付页面然后做一系列的操作
     */
    /*@RequestMapping(method = RequestMethod.POST,value = "/orderInformation")
    public String getOrderInformation(
            HttpServletResponse response,
            HttpServletRequest request
    )throws Exception{
        return null;
    }*/

    /**
     * 这个主要是用来实现小米10pro网页跳转的
     */
    @RequestMapping(method = RequestMethod.GET,value = "/xiaomi10pro")
    public void xiaomi10(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/xiaomi_10_pro.html");
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
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);

                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println(test);
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
    public void RedmiK30(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/redMi_k30.html");
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
    @RequestMapping(method = RequestMethod.POST,value = "/RedmiK30")
    @ResponseBody
    public Map<String,String> doPostRedmiK30(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现黑鲨手机网页跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/blackShark")
    public void blackShark(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/blackShark.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/blackShark")
    @ResponseBody
    public Map<String,String> doPostBlackShark(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了Redmi 9A手机页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/redmi9A")
    public void redmi9A(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/Redmi9A.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/redmi9A")
    @ResponseBody
    public Map<String,String> doPostredmi9A(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了小米10青春版的手机页面跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/xiaomi10youth")
    public void xiaomi10youth(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/Mi10YouthEdition5G.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/xiaomi10youth")
    @ResponseBody
    public Map<String,String> doPostXiaomi10youth(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了小米10页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/xiaomi10")
    public void xiaomi10r(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/xiaomi_10.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/xiaomi10")
    @ResponseBody
    public Map<String,String> doPostXiaomi101(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了redmik30页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/redMiK30pro")
    public void redmik30(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/RedmiK30Pro.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/redMiK30pro")
    @ResponseBody
    public Map<String,String> doPostredMiK30pro(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了RedmiK30ProZoomVersion页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/RedmiK30ProZoomVersion")
    public void RedmiK30ProZoomVersion(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/RedmiK30ProZoomVersion.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/RedmiK30ProZoomVersion")
    @ResponseBody
    public Map<String,String> doPostRedmiK30ProZoomVersion(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了RedmiSmartTVX65页面的跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/RedmiSmartTVX65")
    public void RedmiSmartTVX65(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/RedmiSmartTVX65.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/RedmiSmartTVX65")
    @ResponseBody
    public Map<String,String> doPostRedmiSmartTVX65(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    @RequestMapping(method = RequestMethod.GET,value = "/RedmiSmartTVX70")
    public void RedmiSmartTVX70(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/RedmiTV70inches.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/RedmiSmartTVX70")
    @ResponseBody
    public Map<String,String> doPostRedmiSmartTVX70(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了fullScreenTVE55A页面的跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/fullScreenTVE55A")
    public void fullScreenTVE55A(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/FullScreenTVE55A.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/fullScreenTVE55A")
    @ResponseBody
    public Map<String,String> doPostfullScreenTVE55A(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了MiFullScreenTVE32C页面的跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/miFullScreenTVE32C")
    public void miFullScreenTVE32C(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/MiFullScreenTVE32C.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/miFullScreenTVE32C")
    @ResponseBody
    public Map<String,String> doPostmiFullScreenTVE32C(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     *这个实现了MijiaAirConditioning页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/mijiaAirConditioning")
    public void mijiaAirConditioning(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/MijiaAirConditioning.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/mijiaAirConditioning")
    @ResponseBody
    public Map<String,String> doPostmijiaAirConditioning(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了小米电视4A 60英寸页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/miTV4A60inches")
    public void miTV4A60inches(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/MiTV4A60inches.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/miTV4A60inches")
    @ResponseBody
    public Map<String,String> doPostmiTV4A60inches(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了米家互联网洗烘一体机Pro 10kg的页面跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/mijiaWashingMachine")
    public void mijiaWashingMachin(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/mijiaWashingMachine.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/mijiaWashingMachine")
    @ResponseBody
    public Map<String,String> doPostmijiaWashingMachine(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了Redmi全自动波轮洗衣机1A 8kg的页面跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/redMiWashingMachine")
    public void redmiWashingMachine(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/redMiWashingMachine.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/redMiWashingMachine")
    @ResponseBody
    public Map<String,String> doPostredMiWashingMachine(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了小米电脑页面的页面跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/computer1")
    public void computer1(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/computer1.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/computer1")
    @ResponseBody
    public Map<String,String> doPostcomputer1(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了小米电脑air的跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/computer2")
    public void computer2(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/computer2.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/computer2")
    @ResponseBody
    public Map<String,String> doPostcomputer2(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了MIXAlpha手机页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/mixAlpha")
    public void mixAlpha(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/MIXAlpha.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/mixAlpha")
    @ResponseBody
    public Map<String,String> doPostmixAlpha(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了Redmi10XPro手机页面的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/redmi10XPro")
    public void redmi10XPro(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/Redmi10XPro5G.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/redmi10XPro")
    @ResponseBody
    public Map<String,String> doPostredmi10XPro(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个实现了小米手环4的跳转
     */
    @RequestMapping(method = RequestMethod.GET,value = "/miBand4")
    public void miBand4(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/MiBand4.html");
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

    @RequestMapping(method = RequestMethod.POST,value = "/miBand4")
    @ResponseBody
    public Map<String,String> doPostmiBand4(
            @RequestParam Map<String,String>info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("phoneName");
        String productName = name.toString();
        System.out.println(productName);
        Object name2 = info.get("price");
        String price = name2.toString();
        //判断前端用户有没有登录
        Object user = request.getSession().getAttribute("userName");
        consumer = user.toString();
        if (consumer.length() != 0){
            //当用户添加购物车的时候，先去数据库中查询下有无此用户的购物记录
            Boolean a = adminUserShoppingCartService.queryShoppingRecords(consumer,productName);
            System.out.println("这个是购物车中的用户名"+a);
            if (a == null){
                //如果登录了并且数据库中没有数据就获取session然后将加入购物车的数据添加到数据库中去,让数据库中的数据+1
                adminUserShoppingCartService.settlement(consumer,productName,price,1);
            }else{
                //如果登录了就先去查询这个用户购买的商品数量是多少
                String  quantity = adminUserShoppingCartService.checkProductQuantity(consumer,productName);
                /*System.out.println("该用户购买的数量是"+quantity);*/
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                System.out.println("xxxxx"+quantity);
                //将查询出来的数据进行强制转换，把String类型转换成为int类型
                int quantity1 = Integer.parseInt(quantity);
                //将查询出来的数据+1
                int quantity2 = quantity1+1;
                //将+1的数据写入数据库存储
                int test = adminUserShoppingCartService.addQuantity(quantity2,consumer,productName);
                System.out.println("ooo"+test);
                //将+1的数据写入数据库存储
                /*adminUserShoppingCartService.addQuantity(quantity3,consumer);*/
            }
        }else{
            //如果没有登录就在cookie中去添加数据

            //如果在添加cookie之后再登录的就在登录之后去数据库中添加数据

        }
        return info;
    }
    /**
     * 这个是用来实现结算购物车功能的
     * @return
     * 1.先查出这个属于用户所有的商品信息
     * 2.将所有的商品信息展示在前端
     * 3.当用户在点击结算按钮的时候结算前端页面购物车中所有商品的价格
     */
    @RequestMapping(method = RequestMethod.GET,value = "/QueryProductInformation")
    public void shoppingCart(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/CollectionOfProductPages/ShoppingCart.html");
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

    @RequestMapping(method = RequestMethod.GET,value = "/QueryProductInformation/list")
    public  void shoppingCart1(
            HttpServletRequest request,
            HttpServletResponse response
    )throws Exception{
        //从前端获取用户登录之后的session
        String user = (String) request.getSession().getAttribute("userName");
        List text =  adminUserShoppingCartService.queryProductInformation(user);

        String a = text.toString();
        int mysqlData = text.size();
        /*list.add(a);*/
        for (int i=0;i<text.size();i++){
            String Data = text.get(i).toString().substring(9);
            JSONObject jsonData = JSONObject.fromObject(Data);
            jsonAddList = jsonData.toString();
            list.add(jsonAddList);
        }
        JSONArray jsonArray1 = JSONArray.fromObject(list);
        String change = jsonArray1.toString();
        String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
        String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
        int totalCount = 1; // 总记录数(应根据数据库取得，在此只是模拟)
        int totalPage = totalCount % Integer.parseInt(rows) == 0 ? totalCount
                / Integer.parseInt(rows) : totalCount / Integer.parseInt(rows)
                + 1; // 计算总页数
        int index = (Integer.parseInt(page) - 1) * Integer.parseInt(rows); // 开始记录数
        int pageSize = Integer.parseInt(rows);
        // 以下模拟构造JSON数据对象
        String json = "{data: {"+"totalCount:" + totalCount + ", pageSize: " + pageSize
                + ", totalPage: " + totalPage + ", list: "+change+"}}";
        JSONObject testjson = JSONObject.fromObject(json);
        String change1 =testjson.toString();
        System.out.println("响应到前端的json:"+change1);
        response.getWriter().write(change1);
        //以下这个代码主要是用来防止当用户不断刷新前端页面的时候，list中会不停的增长的问题
        list.clear();
    }

    /**
     * 这个是用来跳转到用户选择地址的页面的
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/confirmOrder")
    public  void confirmOrder(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/confirmOrder.html");
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
     * 这个是用来跳转到支付宝付款页面的
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/alipay")
    public void alipay(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/alipay.html");
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
     * 前端
     */
    @RequestMapping(method = RequestMethod.POST,value = "/modifiedProductQuantity")
    @ResponseBody
    public Map<String,String> modifiedProductQuantity(
            @RequestParam Map<String,String> info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ){
        Object name =info.get("quantity");
        String quantity = name.toString();
        Object id =info.get("id");
        String id1 = id.toString();

        int id2 = Integer.parseInt(id1);
        String userName = request.getSession().getAttribute("userName").toString();
        /*System.out.println("数量是："+productName+"id是:"+id2);*/
        String user = (String) request.getSession().getAttribute("userName");
        /*Unquotted string ''
        出现这个情况的时候的解决办法：
        1.从数据库中取出的list数据不能转换成String类型直接用JSONArray即可
        2.看下自己的方法写错没有
         */
        List<OrderList>text =  adminUserShoppingCartService.queryProductInformation(user);
        JSONArray text3 = JSONArray.fromObject(text);

        for (int i = 0;i<id2;i++){
            String text4 = text3.getString(i);
            JSONObject text5 = JSONObject.fromObject(text4);
            String text6 = text5.toString();
            list.add(text6);
            /*System.out.println(text6);*/
        }
        /*
        {"imgurl":"","quantity":17,"price":"2199","consumer":"123456","productName":"Redmi K30 至尊纪念版 8GB+128GB 极夜黑"}
         */
        String text7 = list.get(id2-1).toString();
        JSONObject text8 = JSONObject.fromObject(text7);
        String productName1 = text8.getString("productName");
        System.out.println(productName1);
        int updateQuantity =  adminUserShoppingCartService.updateQuantity(quantity,userName,productName1);
        //将获取到的数据以及字
        list.clear();
        System.out.println(updateQuantity);

        return null;
    }



}

