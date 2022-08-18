package com.px.blog.dao;

import com.px.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select t from t_comment t where t.parentComment is null and t.blog.id = ?1")
    List<Comment> findByBlogIdAndParentCommentNot(Long blogId, Sort sort);
}
