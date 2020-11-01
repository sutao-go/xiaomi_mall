package coom.imooc.config;

import coom.imooc.interceptor.BackendInterceptorConfiguration;
import coom.imooc.interceptor.FrontEndSystemAuthenticationInterceptor;
import coom.imooc.interceptor.PayIntercepter;
import coom.imooc.interceptor.PersonalCenterIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * 搞定拦截器
 */
@Configuration
public class XiaoMiMallWebMvcConfigurer implements WebMvcConfigurer{

    @Autowired
    private FrontEndSystemAuthenticationInterceptor frontEndSystemAuthenticationInterceptor;
    @Autowired
    private PersonalCenterIntercepter personalCenterIntercepter;
    @Autowired
    private PayIntercepter payIntercepter;
    @Autowired
    private BackendInterceptorConfiguration backendInterceptorConfiguration;
    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

    }

    @Override
    public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/admin为前缀的url路径（后台登陆拦截）
        registry.addInterceptor(frontEndSystemAuthenticationInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/kaptcha")
                .excludePathPatterns("/admin/plugins/**");
        registry.addInterceptor(personalCenterIntercepter)
                .addPathPatterns("/user/**");
        registry.addInterceptor(payIntercepter)
                .addPathPatterns("/orderlist/**");
        registry.addInterceptor(backendInterceptorConfiguration)
                .addPathPatterns("/backendLogin/**")
                .excludePathPatterns("/backendLogin/login");

    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {

    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

    }
}
