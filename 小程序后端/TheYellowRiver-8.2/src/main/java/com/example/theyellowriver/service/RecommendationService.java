package com.example.theyellowriver.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.theyellowriver.dao.ArticleFavoritesMapper;
import com.example.theyellowriver.dao.ArticleHistoryMapper;
import com.example.theyellowriver.pojo.ArticleFavorites;
import com.example.theyellowriver.pojo.ArticleHistory;
import com.example.theyellowriver.pojo.User;
import com.example.theyellowriver.utils.UserThreadLocal;
import com.example.theyellowriver.vo.ArticleVo;
import com.example.theyellowriver.vo.params.PageParams;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 14158
 */
@Service
public class RecommendationService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    ArticleHistoryMapper articleHistoryMapper;
    @Resource
    ArticleFavoritesMapper articleFavoritesMapper;
    @Resource
    ArticleService articleService;

    public List<ArticleVo> generateRecommendations(HttpServletRequest request) throws TasteException {
        // 获取所有用户的历史记录和收藏
        List<ArticleHistory> histories = articleHistoryMapper.selectList(new QueryWrapper<>());
        List<ArticleFavorites> favorites = articleFavoritesMapper.selectList(new QueryWrapper<>());

        // 创建一个用户-文章矩阵，用于存放用户的偏好信息
        FastByIDMap<PreferenceArray> preferences = new FastByIDMap<>();

        FastByIDMap<FastIDSet> tempPreferences = new FastByIDMap<>();
        FastByIDMap<FastByIDMap<Float>> preferenceValues = new FastByIDMap<>();

        for (ArticleHistory history : histories) {
            long userId = history.getUserId();
            long itemId = history.getArticleId();

            if (!tempPreferences.containsKey(userId)) {
                tempPreferences.put(userId, new FastIDSet());
                preferenceValues.put(userId, new FastByIDMap<>());
            }

            tempPreferences.get(userId).add(itemId);
            preferenceValues.get(userId).put(itemId, 1.0f);
        }

        for (ArticleFavorites favorite : favorites) {
            long userId = favorite.getUserId();
            long itemId = favorite.getArticleId();

            if (!tempPreferences.containsKey(userId)) {
                tempPreferences.put(userId, new FastIDSet());
                preferenceValues.put(userId, new FastByIDMap<>());
            }

            tempPreferences.get(userId).add(itemId);
            preferenceValues.get(userId).put(itemId, 2.0f);
        }

        for (Map.Entry<Long, FastIDSet> entry : tempPreferences.entrySet()) {
            long userId = entry.getKey();
            FastIDSet itemSet = entry.getValue();
            FastByIDMap<Float> userPreferenceValues = preferenceValues.get(userId);

            PreferenceArray prefArray = new GenericUserPreferenceArray(itemSet.size());

            int index = 0;
            for (long itemId : itemSet) {
                prefArray.setUserID(index, userId);
                prefArray.setItemID(index, itemId);
                prefArray.setValue(index, userPreferenceValues.get(itemId));
                index++;
            }

            preferences.put(userId, prefArray);
        }


        // 使用上述偏好矩阵构建数据模型，供推荐引擎使用
        DataModel model = new GenericDataModel(preferences);

        // 计算用户之间的相似度，这里使用皮尔逊相关系数
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

        // 定义用户的邻域为与其最相似的5个用户
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);

        // 创建一个基于用户的推荐引擎
        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
        User user = null;
        if (request != null) {
            String token = request.getHeader("Authorization");
            String s = redisTemplate.opsForValue().get("TOKEN_" + token);
            user = JSON.parseObject(s, User.class);
            if (user == null) {
                PageParams pageParams = new PageParams();
                pageParams.setPageSize(5);
                return articleService.listArticle(pageParams);
            }
        }
        assert user != null;
        Long userId = user.getId();

        // 为指定的用户生成前3个推荐
        List<RecommendedItem> recommendations = recommender.recommend(userId, 3);

        // 将推荐的结果转换为文章列表
        List<ArticleVo> recommendedArticles = new ArrayList<>();
        for (RecommendedItem item : recommendations) {
            long articleId = item.getItemID();
            // 根据文章id获取文章详情
            ArticleVo article = articleService.findArticleById(articleId, false, null);
            recommendedArticles.add(article);
        }

        System.out.println(5);
        System.out.println(recommendedArticles);
        if (recommendedArticles.isEmpty()){
            return articleService.hotArticle(5);
        }
        // 返回推荐的文章
        return recommendedArticles;
    }

}