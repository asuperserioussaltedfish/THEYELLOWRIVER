package com.example.management.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.management.dao.dos.Archives;
import com.example.management.pojo.Article;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 14158
 */
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 文章列表
     * @param page 文章
     * @param categoryId 组别id
     * @param tagId 标签id
     * @param year 年份
     * @param month 月份
     * @return IPage<Article>
     */
    IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);

    /**
     * 文章归档
     * @return List<Archives>
     */
    List<Archives> listArchives();

    /**
     * 查询用户的收藏文章的id
     * @param userId 用户id
     * @return Long
     */
    List<Long> userFavorites(Long userId);

    /**
     * 查询
     * @param body 查询文章
     * @return List
     */
    List<String> fuzzyQueries(String body);

    /**
     * 文章总数量
     * @return int
     */
    @Select("select count(*) as allArticle from article")
    Integer numberOfArticles();
    /**
     * 当日新增文章
     * @return int
     */
    @Select("SELECT COUNT(*) AS new_articles_count FROM article " +
            "WHERE DATE(FROM_UNIXTIME(round(create_date/1000,0))) = CURDATE()")
    Integer newArticles();

    /**
     * 查询需要审核的文章
     * @return List<Article></>
     */
    @Select("select * from article where audit_status =2")
    List<Article> selectCheckArticle();


}
