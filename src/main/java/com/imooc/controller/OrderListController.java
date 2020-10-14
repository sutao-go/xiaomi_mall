package com.imooc.controller;

import com.imooc.service.AdminUserShoppingCartService;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orderlist")
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
    public  String confirmOrder(){
        return "templates/frontPage/confirmOrder";
    }

    /**
     * 这个是用来跳转到支付宝付款页面的
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/alipay")
    public String alipay(){

        return "templates/frontPage/alipay";

    }


}

