package com.example.management.pojo;

import lombok.Data;

/**
 * @author 14158
 */
@Data
public class ArticleBody {

    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}