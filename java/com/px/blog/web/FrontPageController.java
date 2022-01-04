package com.px.blog.web;

import com.px.blog.NotFoundException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class FrontPageController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/frontpage")
    public String frontpage(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(20));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(4));
        return "frontpage";
    }

    @PostMapping("/frontpage/search")
    public String search(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        Page<Blog> blogList = blogService.listBlog("%" + query + "%", pageable);
        model.addAttribute("page", blogList);
        model.addAttribute("query", query);
        model.addAttribute("issearch", true);
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(20));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(4));
        if (blogList.getTotalElements() != 0) {
            return "search";
        } else {
            return "searchnotfound";
        }
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        model.addAttribute("types", typeService.listTypeTop(6));
        return "blog";
    }
}
