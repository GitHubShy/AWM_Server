package com.inft.awm.configuration;


import com.inft.awm.interceptor.AuthenticationInterceptor;
import com.inft.awm.response.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@Configuration
public class GlobalConfig implements WebMvcConfigurer {

    @Value("${file-save-path}")
    private String fileSavePath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/awm_server/**");
        // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 配置资源映射
         * 意思是：如果访问的资源路径是以“/images/”开头的，
         * 就给我映射到本机的“E:/images/”这个文件夹内，去找你要的资源
         * 注意：E:/images/ 后面的 “/”一定要带上
         */
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:"+fileSavePath);
    }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @RestControllerAdvice
    static class ResultResponseAdvice implements ResponseBodyAdvice<Object> {
        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
            if (body instanceof Result) {
                return body;
            }
            return new Result(body);
        }
    }
}
