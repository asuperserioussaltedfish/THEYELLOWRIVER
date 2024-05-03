package com.example.theyellowriver.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 14158
 */
@Data
@TableName("user_favorites")
public class ArticleFavorites {
    private Long id;

    private Long articleId;

    private Long userId;
}