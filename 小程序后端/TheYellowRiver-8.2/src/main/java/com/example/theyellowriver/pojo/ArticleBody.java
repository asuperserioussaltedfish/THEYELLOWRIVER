package com.example.theyellowriver.pojo;

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