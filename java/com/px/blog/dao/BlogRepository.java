package com.px.blog.dao;

import com.px.blog.po.Blog;
import com.px.blog.po.Tag;
import com.px.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from t_blog b where b.recommend=true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from t_blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update t_blog b set b.views = b.views + 1 where b.id = ?1")
    int updateViews(Long id);

    @Query("select b from t_blog b where b.type = ?1")
    Page<Blog> findByType(Type type, Pageable pageable);

    @Query(nativeQuery = true, value = "select date_format(b.update_time,'%Y') as year from t_blog b where b.published = true group by year ORDER BY year DESC")
    List<String> findGroupByYear();

    @Query(value = "select * from t_blog b where date_format(b.update_time,'%Y') = ?1 and b.published = true",nativeQuery = true)
    List<Blog> findByYear(String year);
}
