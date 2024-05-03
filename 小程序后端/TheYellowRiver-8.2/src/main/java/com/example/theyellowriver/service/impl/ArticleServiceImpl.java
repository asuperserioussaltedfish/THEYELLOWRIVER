package com.example.theyellowriver.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.theyellowriver.dao.*;
import com.example.theyellowriver.dao.dos.Archives;
import com.example.theyellowriver.pojo.*;
import com.example.theyellowriver.service.*;
import com.example.theyellowriver.utils.UserThreadLocal;
import com.example.theyellowriver.vo.*;
import com.example.theyellowriver.vo.params.ArticleParam;
import com.example.theyellowriver.vo.params.PageParams;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.joda.time.DateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 14158
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    ArticleHistoryMapper articleHistoryMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
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
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Resource
    private ArticleFavoritesMapper articleFavoritesMapper;
    @Resource
    private SelectHistoryMapper selectHistoryMapper;
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
        queryWrapper.eq(Article::getAuditStatus,1);
        return queryWrapper;
    }

    @Override
    public List<ArticleVo> hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = queryWrapper(limit);
        queryWrapper.orderByDesc(Article::getViewCounts);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return copyList(articles,false,false);
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
    public ArticleVo findArticleById(Long id, boolean isBody, HttpServletRequest request ) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getId,id);
        if (request!=null) {
            String token = request.getHeader("Authorization");
            String s = redisTemplate.opsForValue().get("TOKEN_" + token);
            User user = JSON.parseObject(s,User.class);
            QueryWrapper<ArticleHistory> articleHistoryQueryWrapper = new QueryWrapper<>();
            if (user!=null) {
                if (articleMapper.selectById(id).getAuditStatus().equals(1)){
                    articleHistoryQueryWrapper.eq("user_id", user.getId()).eq("article_id", id);
                    articleHistoryMapper.delete(articleHistoryQueryWrapper);
                    ArticleHistory articleHistory = new ArticleHistory();
                    articleHistory.setArticleId(id);
                    articleHistory.setUserId(user.getId());
                    articleHistoryMapper.insert(articleHistory);
                }
                if (articleMapper.selectOne(lambdaQueryWrapper).getAuthorId().equals(user.getId())){
                    if (articleMapper.selectById(id).getAuditStatus().equals(1)){
                        threadService.updateViewCount(articleMapper,articleMapper.selectOne(lambdaQueryWrapper));
                    }
                    return copy(articleMapper.selectOne(lambdaQueryWrapper),true,true,isBody,false);
                }
            }
        }
        lambdaQueryWrapper.eq(Article::getAuditStatus,1);
        Article article = articleMapper.selectOne(lambdaQueryWrapper);
        threadService.updateViewCount(articleMapper,article);
        return copy(article,true,true,isBody,false);
    }
    @Override
    public List<ArticleVo> getUserFavoritesArticles() {
        User user = UserThreadLocal.get();
        List<ArticleVo> articleVoList = new ArrayList<>();
        //获取文章id列表
        List<Long> articles=articleMapper.userFavorites(user.getId());
        for (Long articleId:articles) {
            //把文章查出来
            ArticleVo articleVo =findArticleById(articleId,false,null);
            articleVoList.add(articleVo);
        }
        return articleVoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result publish(ArticleParam articleParam) {
        User user = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(user.getId());
        System.out.println(111);
        System.out.println(articleParam);
        article.setCategoryId(articleParam.getCategory().getId());
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setCollection(0);
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L);
        article.setAuditStatus(2);
        this.articleMapper.insert(article);

        //tags
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag.getId());
                this.articleTagMapper.insert(articleTag);
            }
        }
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBody.setArticleId(article.getId());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        return Result.success(articleVo);
    }

    @Override
    public Result setUserFavoritesArticles(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        if(article.getAuditStatus()!=1){
            return Result.success("文章未完成审核，无法收藏");
        }
        User user = UserThreadLocal.get();
        ArticleFavorites articleFavorites = new ArticleFavorites();
        articleFavorites.setArticleId(articleId);
        articleFavorites.setUserId(user.getId());
        articleFavoritesMapper.insert(articleFavorites);

        UpdateWrapper<Article> articleUpdateWrapper = new UpdateWrapper<>();
        articleUpdateWrapper.eq("id",articleId).set("collection",article.getCollection()+1);
        articleMapper.update(null,articleUpdateWrapper);
        return Result.success(null);
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
        User user = UserThreadLocal.get();
        if (user!=null){
            SelectHistory selectHistory = new SelectHistory();
            selectHistory.setUserId(user.getId());
            selectHistory.setContent(body);
            selectHistoryMapper.insert(selectHistory);
        }
         return Result.success(articleMapper.fuzzyQueries(body));
    }

    @Override
    public Result selectHistory() {
        User user = UserThreadLocal.get();
        List<String> content= selectHistoryMapper.selectContent(user.getId());
        return Result.success(content);
    }

    @Override
    public List<ArticleVo> articleHistory() {
        User user = UserThreadLocal.get();
        List<Long> aLong = articleHistoryMapper.selectArticleId(user.getId());
        List<ArticleVo> list =new ArrayList<>();
        for (Long aid :aLong) {
            ArticleVo article = findArticleById(aid, false,null);
            list.add(article);
        }
        return list;
    }

    @Override
    public String deleteUserFavoritesArticles(Long articleId) {
        User user =UserThreadLocal.get();
        QueryWrapper<ArticleFavorites> articleFavoritesQueryWrapper = new QueryWrapper<>();
        articleFavoritesQueryWrapper.eq("user_id",user.getId()).eq("article_id",articleId);
        Article article = articleMapper.selectById(articleId);
        UpdateWrapper<Article> articleUpdateWrapper = new UpdateWrapper<>();
        articleUpdateWrapper.eq("id",articleId).set("collection",article.getCollection()-1);
        articleMapper.update(null,articleUpdateWrapper);
        articleFavoritesMapper.delete(articleFavoritesQueryWrapper);
        return "取消收藏成功";
    }

}
