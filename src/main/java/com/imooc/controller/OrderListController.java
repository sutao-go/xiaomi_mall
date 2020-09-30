package com.imooc.controller;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/orderlist")
public class OrderListController {
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
     * 这个主要是用来实现小米10网页跳转的
     */
    @RequestMapping(method = RequestMethod.GET,value = "/xiaomi10")
    public String xiaomi10(){
        return "templates/frontPage/CollectionOfProductPages/xiaomi_10";
    }
    /**
     * 用于小米10业务处理的
     */
    @RequestMapping(method = RequestMethod.POST,value = "/xiaomi10")
    public String doPostXiaomi10(){
        return null;
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
}

