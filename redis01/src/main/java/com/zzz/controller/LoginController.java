package com.zzz.controller;

import com.zzz.redis.LoginRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dell_2 on 2016/8/26.
 */
@Controller

public class LoginController extends BaseController {

    @Autowired
    private LoginRedis loginRedis;

    @RequestMapping("login")
    @ResponseBody
    public Object login(String userName, String password, HttpServletRequest request){
        return loginRedis.addUserSession(userName,password);
    }

    @RequestMapping("get")
    @ResponseBody
    public Object get(HttpServletRequest request){
        String sessionId = null;
        Cookie[] cks = request.getCookies();
        for (Cookie cookie : cks) {
            if(cookie.getName().equals("sessionId")){
                sessionId = cookie.getValue();
            }
        }
        System.out.println(sessionId);
        return loginRedis.getStrByKey(sessionId);
    }

    @RequestMapping("set")
    @ResponseBody
    public Object set(String sessionId, HttpServletResponse response){
        System.out.println("set sessionId :"+sessionId);
        response.addCookie(new Cookie("sessionId",sessionId));
        return buildParentCallback("1","setSession");
    }
}
