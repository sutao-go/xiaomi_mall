package com.imooc.controller;

import com.imooc.entity.AdminUser;
import com.imooc.service.AdminUserCarouselService;
import com.imooc.service.AdminUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/backendLogin")
public class BackendLogin {

    @Autowired
    AdminUserService adminUserService;

    @Autowired
    AdminUserCarouselService adminUserCarouselService;

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login() {
        return "templates/backgroundPage/BackgroundLogin";
    }
    List<String> list=new ArrayList<>();
    /**
     * 实现后台登录页面的跳转的
     * @param info
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    @ResponseBody
    public Map<String, String> doPostXiaomi10(
            @RequestParam Map<String, String> info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ) throws Exception {
        Object name =info.get("accountnumber");
        String userName = name.toString();
        Object name2 = info.get("password");
        String password = name2.toString();
        AdminUser adminUser = adminUserService.findAdministrator(userName,password);
        JSONObject adminUser1 = JSONObject.fromObject(adminUser);
        String adminUserName = adminUser1.getString("userName");
        String adminPassWord = adminUser1.getString("passWord");
        if (userName.equals(adminUserName)&&password.equals(adminPassWord)){
            request.getSession().setAttribute("userName",adminUserName);
            info.put("resultCode","200");
            System.out.println("success");
            return info;
        }else{
            System.out.println("您的账号和密码可能出现了一些问题，建议您可以检查下您的账号和密码");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "/index")
    public String index(){
        return "templates/backgroundPage/BackgroundIndex";
    }

    /**
     *这个的主要作用是用来修改轮播图用的
     * @param file
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addimg")
    public String uploadFile(
            @RequestParam("file") MultipartFile file
    ) {
            String fileName = file.getOriginalFilename();
            /*String path = "C:/Users/Admin/IdeaProjects/xiaomi_mall/src/main/webapp/resources/upload/";73*/
            String path = "C:/test/";
            File newFile = new File(path + fileName);
            String showFile = newFile.toString().substring(8);
            String showFilePath ="../../resources/upload/"+showFile;
            System.out.println(showFilePath);
            /*String userName = request.getSession().getAttribute("userName").toString();
            int updateCarousel = adminUserCarouselService.updateCarousel(showFilePath,userName);*/
        try {
            file.transferTo(newFile);
            return "成功";
        }
        catch (Exception e){
            e.printStackTrace();
            return "失败";
        }
    }
    /**
     * 这个是将后端数据库中的轮播图数据读取到后台
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET,value = "/editPicture")
    public void showFile(
            HttpServletRequest request,
            HttpServletResponse response
    )throws Exception{
        String allCarousel=null;
        List findCarousel = adminUserCarouselService.findCarousel(allCarousel);
        String a =findCarousel.toString();
        JSONArray jsonArray = JSONArray.fromObject(findCarousel);
        int data = findCarousel.size();
        for (int i = 0;i<findCarousel.size();i++){
            String Data = jsonArray.get(i).toString().substring(0);
            JSONObject jsonObject = JSONObject.fromObject(Data);
            String a1 = jsonObject.toString();
            list.add(a1);
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

}
