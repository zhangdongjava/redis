package com.zzz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zzz.pojo.User;
import com.zzz.redis.LoginRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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

public class LoginController extends  BaseController {

    @Autowired
    private LoginRedis loginRedis;

    @RequestMapping("login")
    @ResponseBody
    public Object login(User user, HttpServletRequest request, HttpServletResponse response){
        String sessionId = request.getSession().getId();
        Cookie cookie = new Cookie("sessionId",sessionId);
        response.addCookie(cookie);
        String res;
        try {
            res = buildLoginResult(sessionId);
            loginRedis.adddUserSession(sessionId,ObjectToJson(user));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            res = buildParentCallback("\""+e.toString()+"\"","callback");
        }
        System.out.println("初始化sessionId:"+sessionId);
        return res;
    }




    private String buildLoginResult(String sessionId) throws JsonProcessingException {
        String[] urls = {"http://localhost:8083/set?sessionId="+sessionId};
        return this.buildParentCallback(urls,"callback");
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
        System.out.println(request.getSession().getId());
        return loginRedis.getStrByKey(sessionId);
    }
}
