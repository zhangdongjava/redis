package com.zzz.controller;

import com.zzz.pojo.User;
import com.zzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Executors;

/**
 * Created by dell_2 on 2016/8/3.
 */
@Controller
@RequestMapping("/")
public class TestController {

    @Autowired
    private UserService userService;


    @RequestMapping("test")
    @ResponseBody
    public String test(Integer id,double payNum){
       try {
           User user = new User();
           user.setId(id);
           user.setPayNum(payNum);
           return Boolean.toString(userService.add(user));
       }catch (Exception e){
           e.printStackTrace();
       }
        return "false";
    }

    @RequestMapping("list")
    @ResponseBody
    public Object list(){
        try {

           return userService.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "[]";
    }
}
