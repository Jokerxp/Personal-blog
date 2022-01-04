package com.px.blog.web;

import com.px.blog.po.Blog;
import com.px.blog.service.BlogService;
import com.px.blog.service.TagService;
import com.px.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TagShowController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/tags/{id}")
    public String searchTag(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                             @PathVariable Long id, Model model) {
        if(id != -1) {
            Page<Blog> blogList = blogService.listBlogByTagId(id, pageable);
            model.addAttribute("page", blogList);
            model.addAttribute("query", tagService.getTag(id).getName());
        }else{
            Page<Blog> blogList = blogService.listBlog(pageable);
            model.addAttribute("page", blogList);
        }
        model.addAttribute("activeTagId",id);
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("alltags",tagService.listTagTop(10000));
        model.addAttribute("tags", tagService.listTagTop(20));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(4));
        return "tags";
    }
}

