package com.example.theyellowriver.service;

import com.example.theyellowriver.vo.Result;
import com.example.theyellowriver.vo.TagVo;

import java.util.List;

/**
 * @author 14158
 */
public interface TagService {
    /**
     * 通过文章id查找所有标签
     * @param articleId 文章id
     * @return List<TagVo>
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 最热标签
     * @param limit 查询数量
     * @return Result
     */
    Result hots(int limit);

    /**
     * 查找所有
     * @return Result
     */
    List<TagVo> findAll();

    /**
     * 查找所有
     * @return 查找所有
     */
    Result findAllDetail();

    /**
     * 根据标签id查找标签
     * @param id 标签id
     * @return Result
     */
    Result findDetailById(Long id);
}
