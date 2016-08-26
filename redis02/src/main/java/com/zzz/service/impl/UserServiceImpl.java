package com.zzz.service.impl;

import com.zzz.dao.UserDao;
import com.zzz.pojo.User;
import com.zzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dell_2 on 2016/8/3.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public boolean add(User user) {
        return userDao.add(user);
    }

    public List<User> list() {
        return userDao.list();
    }
}
