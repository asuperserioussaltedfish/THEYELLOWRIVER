package com.example.theyellowriver.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.theyellowriver.common.cache.Cache;
import com.example.theyellowriver.dao.ArticleFavoritesMapper;
import com.example.theyellowriver.dao.ArticleMapper;
import com.example.theyellowriver.dao.CategoryMapper;
import com.example.theyellowriver.dao.dos.Archives;
import com.example.theyellowriver.handler.Fish;
import com.example.theyellowriver.pojo.Article;
import com.example.theyellowriver.pojo.ArticleFavorites;
import com.example.theyellowriver.pojo.Category;
import com.example.theyellowriver.pojo.User;
import com.example.theyellowriver.service.ArticleService;
import com.example.theyellowriver.service.TagService;
import com.example.theyellowriver.utils.UserThreadLocal;
import com.example.theyellowriver.vo.ArticleVo;
import com.example.theyellowriver.vo.CategoryVo;
import com.example.theyellowriver.vo.Result;
import com.example.theyellowriver.vo.TagVo;
import com.example.theyellowriver.vo.params.ArticleParam;
import com.example.theyellowriver.vo.params.PageParams;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.aliyun.tea.utils.StringUtils.isEmpty;


/**
 * @author 14158
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private TagService tagService;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ArticleService articleService;
    @Resource
    private ArticleFavoritesMapper articleFavoritesMapper;
    /**
     * 首页 文章列表
     * @param pageParams 页面参数
     * @return Result
     */
    @Fish
    @PostMapping()
    public Result articles(@RequestBody PageParams pageParams){
        return Result.success(articleService.listArticle(pageParams));
    }
    /**
     * 利用缓存存储最热文章
     */
    @Fish
    @Cache(expire = 5 * 60 * 1000,name = "hot_article")
    @PostMapping("hot")
    public Result hotArticle(){
        int limit = 5;
        return Result.success(articleService.hotArticle(limit));
    }

    /**
     * 最新文章
     * @return Result
     */
    @Fish
    @PostMapping("new")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 文章归档
     * @return Result
     */
    @Fish
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }
    /**
     * 浏览文章
     * @param id 文章id
     * @return result
     */
    @Fish
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long id,HttpServletRequest request) {
        ArticleVo articleVo = articleService.findArticleById(id,true, request);
        return Result.success(articleVo);
    }

    /**
     * 查询用户收藏的文章
     * @return result
     */
    @GetMapping("favorites")
    public Result getFavorites(){
        return Result.success(articleService.getUserFavoritesArticles());
    }
    /**
     * 收藏文章
     * @param articleId 文章id
     * @return Result
     */
    @PostMapping("setFavorites/{articleId}")
    public Result setFavorites(@PathVariable("articleId") Long articleId){
        return articleService.setUserFavoritesArticles(articleId);
    }
    /**
     * 发布文章
     * @param articleParam 文章参数
     * @return Result
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }

    /**
     * 最多收藏的文章
     * @return favoriteList
     */
    @Fish
    @PostMapping("favoriteList")
    public Result favoriteLst(){
        int limit = 5;
        return articleService.favoriteList(limit);
    }

    /**
     * 查询文章
     */
    @Fish
    @PostMapping("search")
    public Result searchArticle(@RequestBody ArticleParam articleParam){
        int limit = 5;
        return articleService.searchArticle(limit,articleParam.getTitle());
    }
    /**
     * 查询查询记录
     */
    @PostMapping("selectHistory")
    public Result selectHistory(){
        return articleService.selectHistory();
    }
    /**
     * 查询历史记录
     */
    @PostMapping("articleHistory")
    public Result articleHistory(){
        return Result.success(articleService.articleHistory());
    }

    /**
     * 返回分类列表以及返回山东的文章
     * @return 分类列表以及山东的文章
     */
    @Fish
    @PostMapping("category")
    public Result category(){
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        //页面交互的对象
        List<Object> objects = new ArrayList<>();


        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categories) {
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(category,categoryVo);
            categoryVoList.add(categoryVo);
        }
        objects.add(categoryVoList);
        PageParams pageParams=new PageParams();
        pageParams.setPageSize(100);
        pageParams.setCategoryId(9L);
        objects.add(articles(pageParams));
        return Result.success(objects);
    }
    /**
     * 取消收藏文章
     * @param articleId 文章id
     * @return Result
     */
    @PostMapping("deleteFavorites/{articleId}")
    public Result deleteFavorites(@PathVariable("articleId") Long articleId){
        return Result.success(articleService.deleteUserFavoritesArticles(articleId));
    }
    /**
     * 返回标签列表以及返回第一个标签的文章
     * @return 分类列表以及山东的文章
     */
    @Fish
    @PostMapping("tags")
    public Result tags(){
//        List<Tag> list= tagmapper.selectList(new QueryWrapper<>());

        List<Object> objects = new ArrayList<>();
        List<TagVo> allTag = tagService.findAll();
        objects.add(allTag);
        PageParams pageParams=new PageParams();
        pageParams.setPageSize(100);
        pageParams.setTagId(1L);
        objects.add(articleService.listArticle(pageParams));
        return Result.success(objects);
    }
    /**
     * 返回归档列表以及最新日期的文章
     * @return 归档列表以及最新日期的文章
     */
    @Fish
    @PostMapping("newTime")
    public Result newTime(){
        List<Archives> archivesList = articleMapper.listArchives();
        List<List<Object>> list =new ArrayList<>();

        Integer year=0;

        List<Object> temp2 =new ArrayList<>();
        for (Archives archives:archivesList) {
            if (!archives.getYear().equals(year)) {
                List<Object> temp =new ArrayList<>();
                temp.add("year:");
                temp.add(archives.getYear());
                year = archives.getYear();
                temp.add("month:");
                List<Object> month = new ArrayList<>();
                for (Archives archives1 : archivesList) {
                    if (archives1.getYear().equals(archives.getYear())) {
                        month.add(archives1.getMonth());
                    }
                }
                temp.add(month);
                temp2.add(temp);
            }
        }
        list.add(temp2);
        //页面交互的对象
        List<Object> objects = new ArrayList<>();
        objects.add(list);
        Archives archives = archivesList.get(0);
        PageParams pageParams=new PageParams();
        pageParams.setPageSize(100);
        pageParams.setMonth(archives.getMonth().toString());
        pageParams.setYear(archives.getYear().toString());
        objects.add(articleService.listArticle(pageParams));
        return Result.success(objects);
    }
    /**
     * 判断收藏状态
     * @return 收藏状态
     */
    @PostMapping("judgeTheCollection/{id}")
    public Result judgeTheCollection(@PathVariable("id") Long articleId) {
        User user = UserThreadLocal.get();
        QueryWrapper<ArticleFavorites> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId()).eq("article_id",articleId);
        List<ArticleFavorites> list=articleFavoritesMapper.selectList(queryWrapper);
        if (list.isEmpty()){
            return Result.success("未收藏");
        }else{
            return Result.success("已收藏");
        }
    }

    /**
     * 查找当前用户写过的文章
     * @return Result
     */
    @PostMapping("articleOfUser")
    public Result articleOfUser(){
        User user=UserThreadLocal.get();
        return Result.success(articleMapper.selectArticleOfUser(user.getId()));
    }
    /**
     * 判断文章是否为当前用户写过的文章
     * @return Result
     */
    public boolean determineArticleOfUser(Long articleId ,Long userId){
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getId,articleId);
        return articleMapper.selectOne(articleLambdaQueryWrapper).getAuthorId().equals(userId);
    }
    /**
     * 删除用户之前写过的文章
     * @return Result
     */
    @PostMapping("deleteArticleOfUser")
    public Result deleteArticleOfUser(@RequestBody Article article){
        User user=UserThreadLocal.get();
        if (determineArticleOfUser(article.getId(),user.getId())){
            LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleLambdaQueryWrapper.eq(Article::getId,article.getId());
            articleLambdaQueryWrapper.eq(Article::getAuthorId,user.getId());
            articleMapper.delete(articleLambdaQueryWrapper);
            return Result.success("已成功删除");
        }
        return  Result.success("删除失败");
    }

}
