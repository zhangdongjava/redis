package com.zzz.service;

import com.zzz.pojo.User;

import java.util.List;

/**
 * Created by dell_2 on 2016/8/3.
 */
public interface UserService {

    boolean add(User user);

    List<User> list();

}
