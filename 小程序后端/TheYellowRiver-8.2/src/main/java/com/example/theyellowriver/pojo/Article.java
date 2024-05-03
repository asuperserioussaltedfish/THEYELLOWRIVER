package com.example.theyellowriver.pojo;

import lombok.Data;

/**
 * @author 14158
 */
@Data
public class Article {

    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String summary;
    /**
     * 评论次数
     */
    private Integer commentCounts;
    /**
     * 浏览次数
     */
    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight;

    private Integer collection;
    /**
     * 创建时间
     */
    private Long createDate;
    private Integer auditStatus;
    private String suggest;
}
