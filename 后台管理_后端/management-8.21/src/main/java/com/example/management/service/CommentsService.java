package com.example.management.service;

import com.example.management.vo.Result;

/**
 * @author 14158
 */
public interface CommentsService {

    /**]
     * 通过文章查询评论
     * @param articleId 文章id
     * @return Result
     */
    Result commentsByArticleId(Long articleId);

}