package com.example.management.service;

import com.example.management.pojo.User;
import com.example.management.query.Status;
import com.example.management.vo.ArticleVo;
import com.example.management.vo.Result;
import com.example.management.vo.params.PageParams;

import java.util.List;

/**
 * @author 14158
 */
public interface ArticleService {

    /**
     * 查找所有文章
     * @param pageParams 文章参数
     * @return Result
     */

    List<ArticleVo> listArticle(PageParams pageParams);

    /**
     * 最新文章
     * @param limit 查询数量
     * @return Result
     */
    Result newArticles(int limit);

    /**
     * 文章归档
     * @return Result
     */
    Result listArchives();

    /**
     * 通过id查询文章
     * @param id 文章id
     * @param isBody 是否显示文章内容
     * @return ArticleVo
     */
    ArticleVo findArticleById(Long id, boolean isBody);
    /**
     * 通过用户id获取收藏文章
     * @param  user 用户
     * @return 文章列表
     */
    List<ArticleVo> getUserFavoritesArticles(User user);



    /**
     * 获取收藏数量最多的文章
     * @param limit 数量
     * @return Result
     */
    Result favoriteList(int limit);

    /**
     * 搜索文章
     * @param limit 5
     * @param body 文章内容
     * @return Result
     */
    Result searchArticle(int limit ,String body);


    /**
     * 文章历史记录
     * @return Result
     */
    List<ArticleVo> articleHistory(User user);


    /**
     * 待审核的文章列表
     * @return ArticleVo
     */
    List<ArticleVo> checkArticleList();

    /**审核文章
     * @param status 状态
     *
     */
    void checkArticle(Status status);
}
