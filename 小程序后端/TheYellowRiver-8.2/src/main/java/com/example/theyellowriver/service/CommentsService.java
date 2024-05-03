package com.example.theyellowriver.service;

import com.example.theyellowriver.vo.Result;
import com.example.theyellowriver.vo.params.CommentParam;

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

    /**
     * 评论功能
     * @param commentParam 评论的参数
     * @return Result
     */
    String comment(CommentParam commentParam);
}