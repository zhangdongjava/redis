package com.zzz.filter;

import com.zzz.redis.LoginRedis;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dell_2 on 2016/8/26.
 */
public class LoginFilter implements Filter {

    private  static Set<String> set = new HashSet<String>();
    static {
        set.add("/set");
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request1, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) request1;
        String url = request.getRequestURI();
        if(set.contains(url)){
            filterChain.doFilter(request,servletResponse);
        }else{
            WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
            LoginRedis lr =  wac.getBean(LoginRedis.class);
            String sessionId = getSessionId(request);
            if(sessionId==null){
                goToLogin((HttpServletResponse) servletResponse);
                return;
            }else{
                String userInfo =  lr.getStrByKey(sessionId);
                if(userInfo==null){
                    goToLogin((HttpServletResponse) servletResponse);
                }else{
                    filterChain.doFilter(request,servletResponse);
                }
            }

        }
    }

    private  void goToLogin(HttpServletResponse servletResponse){
        try {
            servletResponse.sendRedirect("http://localhost:8083/res/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSessionId(HttpServletRequest request){
        String sessionId = null;
        Cookie[] cks = request.getCookies();
        for (Cookie cookie : cks) {
            if(cookie.getName().equals("sessionId")){
                sessionId = cookie.getValue();
            }
        }
        return sessionId;
    }

    public void destroy() {

    }
}
