package com.px.blog.web;

import com.px.blog.service.BlogService;
import com.px.blog.service.TagService;
import com.px.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(20));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(4));
        return "aboutme";
    }
}
