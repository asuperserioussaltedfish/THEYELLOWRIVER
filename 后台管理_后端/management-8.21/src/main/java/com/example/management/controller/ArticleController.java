package com.example.management.controller;

import com.example.management.dao.ArticleMapper;
import com.example.management.pojo.User;
import com.example.management.query.Status;
import com.example.management.service.ArticleService;
import com.example.management.vo.ArticleVo;
import com.example.management.vo.Result;
import com.example.management.vo.params.ArticleParam;
import com.example.management.vo.params.PageParams;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 14158
 */
@RestController
@RequestMapping("/prod-api/articles")
@CrossOrigin(origins = "http://localhost:9528")
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private ArticleMapper articleMapper;
    /**
     * 首页 文章列表
     * @param pageParams 页面参数
     * @return Result
     */
    @PostMapping()
    public Result articles(@RequestBody  PageParams pageParams){
        List<Object> list=new ArrayList<>();
        list.add(articleMapper.numberOfArticles());
        list.add(articleService.listArticle(pageParams));
        return Result.success(list);
    }

    /**
     * 最新文章
     * @return Result
     */
    @PostMapping("new")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 文章归档
     * @return Result
     */
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }
    /**
     * 浏览文章
     * @param id 文章id
     * @return result
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long id) {
        ArticleVo articleVo = articleService.findArticleById(id,true);
        return Result.success(articleVo);
    }

    /**
     * 查询用户收藏的文章
     * @return result
     */
    @PostMapping("favorites")
    public Result getFavorites(@RequestBody User user){
        return Result.success(articleService.getUserFavoritesArticles( user));
    }

    /**
     * 最多收藏的文章
     * @return favoriteList
     */
    @PostMapping("favoriteList")
    public Result favoriteLst(){
        int limit = 5;
        return articleService.favoriteList(limit);
    }

    /**
     * 查询文章
     */
    @PostMapping("search")
    public Result searchArticle(@RequestBody ArticleParam articleParam){
        int limit = 5;
        return articleService.searchArticle(limit,articleParam.getTitle());
    }
    /**
     * 查询用户历史记录
     */
    @PostMapping("articleHistory")
    public Result articleHistory(@RequestBody User user){
        return Result.success(articleService.articleHistory(user));
    }

    /**
     * 待审核的文章列表
     * @return Result
     */
    @PostMapping("checkArticleList")
    @CrossOrigin(origins = "http://localhost:9528")
    public Result checkArticleList(){return Result.success(articleService.checkArticleList());}

    /**
     * 审核文章
     * @param status 装填
     * @return 无
     */
    @PostMapping("checkArticle")
    public Result checkArticle(@RequestBody Status status){
        articleService.checkArticle(status);

        return Result.success(null);
    }
    /**
     * 删除文章
     * @param articleParam 文章
     * @return 无
     */
    @PostMapping("deleteArticle")
    public Result checkArticle(@RequestBody ArticleParam articleParam){
        articleMapper.deleteById(articleParam.getId());
        return Result.success(null);
    }

}
