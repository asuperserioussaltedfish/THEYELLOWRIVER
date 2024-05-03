package com.example.theyellowriver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.theyellowriver.pojo.Tag;

import java.util.List;

/**
 * @author 14158
 */
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询文章列表
     * @param articleId .
     * @return .
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热文章
     * @param limit 数量
     * @return List<Long>
     */
    List<Long> findHotsTagIds(int limit);

    /**
     * 根绝tagId查询标签
     * @param tagIds tagId
     * @return List<Tag>
     * */
    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
