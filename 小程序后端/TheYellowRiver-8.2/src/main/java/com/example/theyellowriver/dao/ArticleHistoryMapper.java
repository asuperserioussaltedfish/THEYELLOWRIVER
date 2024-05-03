package com.example.theyellowriver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.theyellowriver.pojo.ArticleHistory;

import java.util.List;

/**
 * @author 14158
 */
public interface ArticleHistoryMapper extends BaseMapper<ArticleHistory> {
    /**
     * 查找历史文章id
     * @param id 文章id
     * @return 文章id
     */
    List<Long> selectArticleId(Long id);
}
