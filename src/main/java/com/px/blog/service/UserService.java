package com.px.blog.service;

import com.px.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {
    User checkUser(String username, String password);
}
