package com.px.blog.dao;

import com.px.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsernameAndPassword(String name, String password);
}
