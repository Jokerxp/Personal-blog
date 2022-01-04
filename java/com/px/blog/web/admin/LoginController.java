package com.px.blog.web.admin;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.px.blog.po.User;
import com.px.blog.service.UserService;
import com.px.blog.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes){
        username = username.trim();
        User user = userService.checkUser(username, MD5.encryptToMD5(password));
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/login_success";
        }else{
            attributes.addFlashAttribute("message","Wrong username or password.");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
