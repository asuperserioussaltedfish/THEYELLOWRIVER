package com.example.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.management.dao.ArticleBodyMapper;
import com.example.management.dao.ArticleHistoryMapper;
import com.example.management.dao.ArticleMapper;
import com.example.management.dao.dos.Archives;
import com.example.management.pojo.Article;
import com.example.management.pojo.ArticleBody;
import com.example.management.pojo.User;
import com.example.management.query.Status;
import com.example.management.service.*;
import com.example.management.vo.ArticleBodyVo;
import com.example.management.vo.ArticleVo;
import com.example.management.vo.CategoryVo;
import com.example.management.vo.Result;
import com.example.management.vo.params.PageParams;
import jakarta.annotation.Resource;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty;

/**
 * @author 14158
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    ArticleHistoryMapper articleHistoryMapper;
    @Resource
    private ArticleMapper articleMapper;
    /**
     * Transactional(rollbackFor = Exception. Class)：出错就回滚
     * 文章列表
     */
    @Resource
    private ThreadService threadService;
    @Resource
    private ArticleBodyMapper articleBodyMapper;
    @Resource
    private CategoryService categoryService;
    @Resource
    private TagService tagService;
    @Resource
    private UserService userService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ArticleVo> listArticle(PageParams pageParams) {
        //创建分页对象
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        //查询分页对象
        IPage<Article> articlePage = this.articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        return copyList(articlePage.getRecords(),true,true);
    }
    public LambdaQueryWrapper<Article> queryWrapper(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //通过收藏量判断
        queryWrapper.select(Article::getId,
                Article::getTitle,
                Article::getViewCounts,
                Article::getCollection,
                Article::getSummary,
                Article::getCreateDate);
        queryWrapper.last("limit "+limit);
        return queryWrapper;
    }



    private CategoryVo findCategory(Long categoryId) {
        return categoryService.findCategoryById(categoryId);
    }



    private ArticleBodyVo findArticleBody(Long articleId) {
        LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleBody::getArticleId,articleId);

        ArticleBody articleBody = articleBodyMapper.selectOne(queryWrapper);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();

        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    /**
     * 将article对象转换为articleVo对象
     * @param article 文章
     * @return articleVo
     */
    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        //copy一下，把文章的东西copy到文章vo里
        try{
            BeanUtils.copyProperties(article,articleVo);
        }catch (Exception e){
            System.out.println("错误原因"+e);
        }
        //设置一下时间
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //进行返回
        //并不是所有的接口 都需要标签 ，作者信息
        if (isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            if (userService.findUserById(authorId) ==null){
                articleVo.setAuthor("用户已注销");
            }
            else {
                articleVo.setAuthor(userService.findUserById(authorId).getNickname());
            }
        }
        if (isBody){
            ArticleBodyVo articleBody = findArticleBody(article.getId());
            articleVo.setBody(articleBody);
        }
        if (isCategory){

            CategoryVo categoryVo = findCategory(article.getCategoryId());
            articleVo.setCategory(categoryVo);
        }

        return articleVo;
    }

    /**
     * 以列表的形式进行返回
     * @param records 文章
     * @return List<ArticleVo>
     */

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor, false, false));
        }
        return articleVoList;
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = queryWrapper(limit);
        queryWrapper.orderByDesc(Article::getCreateDate);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }


    @Override
    public ArticleVo findArticleById(Long id, boolean isBody ) {
        Article article = articleMapper.selectById(id);
        threadService.updateViewCount(articleMapper,article);
        return copy(article,true,true,isBody,true);
    }
    @Override
    public List<ArticleVo> getUserFavoritesArticles(User user) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        //获取文章id列表
        List<Long> articles=articleMapper.userFavorites(user.getId());
        for (Long articleId:articles) {
            //把文章查出来
            ArticleVo articleVo =findArticleById(articleId,false);
            articleVoList.add(articleVo);
        }
        return articleVoList;
    }



    @Override
    public Result favoriteList(int limit) {
        //收藏量最高的文章
        LambdaQueryWrapper<Article> queryWrapper = queryWrapper(limit);
        //通过收藏量判断
        queryWrapper.orderByDesc(Article::getCollection);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,true,false));
    }

    @Override
    public Result searchArticle(int limit ,String body) {
         return Result.success(articleMapper.fuzzyQueries(body));
    }


    @Override
    public List<ArticleVo> articleHistory(User user) {
        List<Long> aLong = articleHistoryMapper.selectArticleId(user.getId());
        List<ArticleVo> list =new ArrayList<>();
        for (Long aid :aLong) {
            ArticleVo article = findArticleById(aid, false);
            list.add(article);
        }
        return list;
    }

    @Override
    public List<ArticleVo> checkArticleList() {
        return  copyList(articleMapper.selectCheckArticle(),true,true);
    }

    @Override
    public void checkArticle(Status status) {
        UpdateWrapper<Article> articleUpdateWrapper = new UpdateWrapper<>();
        articleUpdateWrapper.eq("id", status.getArticleId());
        if (status.getStatus() == 0) {
            articleUpdateWrapper.set("suggest", status.getSuggest());
        }
        articleUpdateWrapper.set("audit_status", status.getStatus());

        articleMapper.update(null, articleUpdateWrapper);
    }

}
