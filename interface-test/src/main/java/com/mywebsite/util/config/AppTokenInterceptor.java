package com.mywebsite.util.config;

import com.mywebsite.util.annotion.GetUserInfo;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppTokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            GetUserInfo getUserInfo = ((HandlerMethod) handler)
                    .getMethodAnnotation(GetUserInfo.class);
            // 没有声明需要权限,或者声明不验证权限
            if (getUserInfo == null) {
                return true;
            } else {
                System.out.println("拦截器拦截注解指定方法！");
                String token = request.getHeader("token");
                System.out.println("current token**************: " + token);
                return true;
            }
        } else {
            return true;
        }
    }
}
