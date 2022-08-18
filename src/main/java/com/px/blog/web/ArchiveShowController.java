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
public class ArchiveShowController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("archive")
    public String archives(Model model){
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(20));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(4));
        model.addAttribute("archive",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "archive";
    }
}
