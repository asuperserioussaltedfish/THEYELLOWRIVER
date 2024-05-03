package com.example.theyellowriver.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.theyellowriver.dao.dos.Archives;
import com.example.theyellowriver.pojo.Article;
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
     * 查看用户发表过的文章
     * @param id 用户id
     * @return List<Article></>
     */
    @Select("select * from article where author_id =#{id} order by create_date desc")
    List<Article> selectArticleOfUser(Long id);
}
