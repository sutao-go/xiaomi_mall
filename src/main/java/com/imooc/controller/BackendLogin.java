package com.imooc.controller;

import com.imooc.entity.AdminUser;
import com.imooc.entity.SalesManagement;
import com.imooc.service.AdminUserService;
import com.imooc.service.AdminUserCarouselService;
import com.imooc.service.ProductInformationService;
import com.imooc.common.BizException;
import com.imooc.common.Result;
import com.imooc.common.ResultCode;
import com.imooc.common.SessionUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 这个模块主要是后端人员对前端的一些操作
 * 比如像商品上下架，用户账号的封禁，轮播图的更新等
 */
@Controller

@RequestMapping(value = "/backendLogin")
public class BackendLogin {

    @Autowired
    AdminUserService adminUserService;

    @Autowired
    AdminUserCarouselService adminUserCarouselService;

    @Autowired
    ProductInformationService productInformationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //展示路径
    public String showFilePath =null;

    List<String> list=new ArrayList<>();
    /**
     * 实现后台登录页面的跳转的
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public void login(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/backgroundPage/BackgroundLogin.html");
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
     * 后台管理员登录
     * <p>
     * 修复：原实现把管理员身份写入前台 userName，导致前台用户即可通过后台鉴权；
     * 这里写入独立的 adminUserName，并改用 BCrypt 校验（原为明文比对）。
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    @ResponseBody
    public Result<Void> backendLogin(@RequestParam Map<String, String> info, HttpSession session) {
        String userName = info.get("accountnumber");
        String password = info.get("password");
        if (StringUtils.isAnyBlank(userName, password)) {
            throw new BizException(ResultCode.PARAM_ERROR, "账号和密码不能为空");
        }
        AdminUser admin = adminUserService.findAdminByName(userName);
        if (admin == null || !passwordEncoder.matches(password, admin.getPassWord())) {
            throw new BizException(ResultCode.PARAM_ERROR, "账号或密码错误");
        }
        SessionUtil.setAdminName(session, admin.getUserName());
        // 兼容存量代码：部分后台接口仍读取前台 userName
        SessionUtil.setUserName(session, admin.getUserName());
        return Result.ok();
    }

    @RequestMapping(method = RequestMethod.GET,value = "/carousel")
    public void index(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/backgroundPage/BackgroundCarousel.html");
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
     *这个的主要作用是用来上传轮播图并且修改路径用的
     * @param file
     */
    /** 上传目录（运行时目录，不再硬编码开发者机器路径） */
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/upload/";

    /** 允许的图片扩展名白名单 */
    private static final Set<String> ALLOWED_EXT =
            new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "gif"));

    /** 单文件上限 5MB */
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024L;

    private static final Logger log = LoggerFactory.getLogger(BackendLogin.class);

    /**
     * 上传轮播图
     * <p>
     * 加固点（原实现可导致写 Webshell）：
     * 1. 文件名为 UUID 重新生成，杜绝 ../ 路径穿越
     * 2. 扩展名白名单 + 内容大小限制
     * 3. 路径归一化后校验前缀，防止越权写入
     * 4. 异常记日志而非 printStackTrace，失败信息不暴露堆栈
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addimg")
    @ResponseBody
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BizException(ResultCode.PARAM_ERROR, "请选择要上传的图片");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BizException(ResultCode.PARAM_ERROR, "图片大小不能超过 5MB");
        }
        String original = StringUtils.trimToEmpty(file.getOriginalFilename());
        String ext = StringUtils.substringAfterLast(original, ".").toLowerCase();
        if (!ALLOWED_EXT.contains(ext)) {
            throw new BizException(ResultCode.PARAM_ERROR, "仅支持 jpg/jpeg/png/gif 格式");
        }

        String newFileName = UUID.randomUUID().toString().replace("-", "") + "." + ext;
        try {
            Path dir = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
            Files.createDirectories(dir);
            Path target = dir.resolve(newFileName).normalize();
            if (!target.startsWith(dir)) {
                throw new BizException(ResultCode.PARAM_ERROR, "非法文件名");
            }
            file.transferTo(target.toFile());

            String url = "/resources/upload/" + newFileName;
            showFilePath = url;
            return Result.ok(url);
        } catch (IOException e) {
            log.error("上传轮播图失败", e);
            throw new BizException(ResultCode.ERROR, "上传失败，请稍后重试");
        }
    }

    /**
     * 这个是用来修改轮数据库中播图用的
     * @param info
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST,value = "/changeid")
    @ResponseBody
    public Map<String,String> changeId(
            @RequestParam Map<String,String> info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Object name =info.get("id");
        String id = name.toString();
        System.out.println(id);
        System.out.println("恭喜您获取到了路径:"+showFilePath);
        String Path = showFilePath;
        int updateCarousel = adminUserCarouselService.updateCarousel(Path,id);
        System.out.println("数据库数据"+updateCarousel);
        if (updateCarousel == 1){
            info.put("resultCode","200");
            return  info;
        }else {
            info.put("resultCode","405");
            return info;
        }
    };

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

    @RequestMapping(method = RequestMethod.POST,value = "/findimg")
    @ResponseBody
    public Map<String,String> findImg(
            @RequestParam Map<String,String> info,
            HttpServletResponse response,
            HttpServletRequest request,
            HttpSession session
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
        System.out.println(list);
        response.getWriter().write(list.toString());
        list.clear();
        return null;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/sales")
    public void Sales(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/backgroundPage/BackgroundCommodityManagement.html");
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
     * 这个是用来下架前端商城中的商品的
     * @param info
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST,value = "/sales/prohibit")
    @ResponseBody
    public Map<String,String> prohibit(
            @RequestParam Map<String,String> info,
            HttpServletRequest request,
            HttpServletResponse response
    )throws Exception
    {
        Object name =info.get("id");
        String id = name.toString();
        String status = "下架了";
        System.out.println(id);
        int changeSalesStatus = productInformationService.changeSalesStatus(id,status);
        if(changeSalesStatus == 1){
            info.put("resultCode","200");
            return info;
        }else {
            info.put("resultCode","405");
            return info;}
    }
    /**
     * 这个是用来上架前端商城中的商品的
     * @param info
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST,value = "/sales")
    @ResponseBody
    public Map<String,String> sales(
            @RequestParam Map<String,String> info,
            HttpServletRequest request,
            HttpServletResponse response
    )throws Exception
    {
        Object name =info.get("id");
        String id = name.toString();
        String status = "销售中";
        System.out.println(id);
        int changeSalesStatus = productInformationService.changeSalesStatus(id,status);
        if(changeSalesStatus == 1){
            info.put("resultCode","200");
            return info;
        }else {
            info.put("resultCode","405");
            return info;
        }
    }

    /**
     * 将后台数据库销售商品的数据显示在前端
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET,value = "/sales/list")
    public void SalesList(
            HttpServletRequest request,
            HttpServletResponse response
    )throws Exception{
        String allProduct=null;
        List<SalesManagement>findCarousel = productInformationService.findProduct(allProduct);
        String a =findCarousel.toString();
        System.out.println(a);
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

    /**
     * 更改商品销售状态
     * @param info
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST,value = "/getStatus")
    @ResponseBody
    public Map<String,String> getStatus(
            @RequestParam Map<String,String> info,
            HttpServletResponse response,
            HttpServletRequest request
    )throws Exception{
        Object name = info.get("productName");
        String productName = name.toString();
        Object findStatus  = productInformationService.findStatus(productName);
        JSONObject jsonObject = JSONObject.fromObject(findStatus);
        String a = jsonObject.getString("salesStatus");
        if (a.equals("销售中")){
            info.put("resultCode","200");
            return info;
        }if(a.equals("下架了")){
            info.put("resultCode","201");
            return info;
        }else {
            info.put("resultCode","202");
            return info;
        }
    }

    /**
     * 跳转到账号管理页面
     */
    @RequestMapping(method = RequestMethod.GET,value = "/accountManagement")
    public void accountManagement(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/backgroundPage/BackgroundAccountManagement.html");
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
     * 将后台中关于用户账户的信息传输到前端页面中去
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET,value = "/accountManagement/list")
    public void accountManagement1(
            HttpServletRequest request,
            HttpServletResponse response
    )throws Exception{
        String userName=null;
        String userStatus=null;
        List<SalesManagement>findCarousel = adminUserService.findUserStatus(userName,userStatus);
        String a =findCarousel.toString();
        System.out.println(a);
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

    /**
     * 封禁账号
     * @param info
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST,value = "/disableAccount")
    @ResponseBody
    public Map<String,String> disableAccount(
            @RequestParam Map<String,String> info,
            HttpServletResponse response,
            HttpServletRequest request
    )throws Exception{
        Object name = info.get("id");
        String id = name.toString();
        String disableAccount = "禁用";
        int changeStatus = adminUserService.changeUserStatus(disableAccount,id);
        if (changeStatus == 1){
            info.put("resultCode","200");
            return info;
        }else {
            info.put("redultCode","202");
            return info;
        }
    }

    /**
     * 解封账号
     * @param info
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST,value = "/unban")
    @ResponseBody
    public Map<String,String> unban(
            @RequestParam Map<String,String> info,
            HttpServletResponse response,
            HttpServletRequest request
    )throws Exception{
        Object name = info.get("id");
        String id = name.toString();
        String disableAccount = "正常";
        int changeStatus = adminUserService.changeUserStatus(disableAccount,id);
        if (changeStatus == 1){
            info.put("resultCode","200");
            return info;
        }else {
            info.put("redultCode","202");
            return info;
        }
    }
    @RequestMapping(method = RequestMethod.GET,value = "/password")
    public void password(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/backgroundPage/BackgroundChangePassword.html");
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
     * 修改管理员密码
     * <p>
     * 修复：原实现读取了旧密码却从未校验（变量取到即丢弃），
     * 配合鉴权失效可任意重置管理员口令；这里强制 BCrypt 校验旧密码。
     */
    @RequestMapping(method = RequestMethod.POST, value = "/password")
    @ResponseBody
    public Result<Void> changePassword(
            @RequestParam Map<String, String> info,
            HttpSession session
    ) {
        String oldPassword = info.get("password");
        String newPassword = info.get("newpassword");
        String confirmPassword = info.get("newpassword2");

        // 身份从会话获取，未登录时拦截器已拦截
        String adminName = SessionUtil.requireAdmin(session);

        if (StringUtils.isAnyBlank(oldPassword, newPassword, confirmPassword)) {
            throw new BizException(ResultCode.PARAM_ERROR, "原密码与新密码不能为空");
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new BizException(ResultCode.PARAM_ERROR, "两次输入的新密码不一致");
        }
        if (StringUtils.length(newPassword) < 6) {
            throw new BizException(ResultCode.PARAM_ERROR, "新密码至少 6 位");
        }

        AdminUser admin = adminUserService.findAdminByName(adminName);
        if (admin == null || !passwordEncoder.matches(oldPassword, admin.getPassWord())) {
            throw new BizException(ResultCode.PARAM_ERROR, "原密码错误");
        }

        int rows = adminUserService.changePassword(passwordEncoder.encode(newPassword), adminName);
        if (rows != 1) {
            throw new BizException(ResultCode.ERROR, "修改密码失败，请稍后重试");
        }
        return Result.ok();
    }
}
