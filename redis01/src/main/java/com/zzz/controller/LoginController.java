package com.zzz.controller;

import com.zzz.redis.LoginRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
        if(sessionId== null)return "你没有登录!";
        String  userInfo = loginRedis.getStrByKey(sessionId);
        if(userInfo== null)return "你没有登录!";
        return userInfo;
    }

    @RequestMapping("set")
    @ResponseBody
    public Object set(String sessionId, HttpServletResponse response){
        System.out.println("set sessionId :"+sessionId);
        response.addCookie(new Cookie("sessionId",sessionId));
        return buildParentCallback("1","setSession");
    }

    @RequestMapping("setMap")
    @ResponseBody
    public Object setMap(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("a","1");
        map.put("b","2");
        map.put("c","3");
        map.put("d","4");
        loginRedis.setMap("map",map);
        return loginRedis.getMap("map");
    }


    @RequestMapping("getMap")
    @ResponseBody
    public Object getMap(String sessionId, HttpServletResponse response){
        System.out.println("set sessionId :"+sessionId);
        response.addCookie(new Cookie("sessionId",sessionId));
        return buildParentCallback("1","setSession");
    }
}
