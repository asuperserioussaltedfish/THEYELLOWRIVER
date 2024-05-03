package com.example.theyellowriver.service;

import com.example.theyellowriver.vo.ArticleVo;
import com.example.theyellowriver.vo.Result;
import com.example.theyellowriver.vo.params.ArticleParam;
import com.example.theyellowriver.vo.params.PageParams;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

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
     * 最热文章
     * @param limit 查询数量
     * @return Result
     */
    List<ArticleVo> hotArticle(int limit);

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
     * @param request 请求头
     * @return ArticleVo
     */
    ArticleVo findArticleById(Long id, boolean isBody, HttpServletRequest request);
    /**
     * 通过用户id获取收藏文章
     * @return 文章列表
     */
    List<ArticleVo> getUserFavoritesArticles();

    /**
     * 发布文章
     * @param articleParam 文章参数
     * @return Result
     */
    Result publish(ArticleParam articleParam);

    /**
     * 设置收藏文章
     * @param articleId 文章id
     * @return Result
     */
    Result setUserFavoritesArticles(Long articleId);

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
     * 查询搜索记录
     * @return Result
     */
    Result selectHistory();

    /**
     * 文章历史记录
     * @return Result
     */
    List<ArticleVo> articleHistory();

    /**
     * 取消收藏文章
     * @param articleId 文章id
     * @return Result
     */
    String deleteUserFavoritesArticles(Long articleId);
}
