package com.example.management.pojo;

import lombok.Data;

/**
 * @author 14158
 */
@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}