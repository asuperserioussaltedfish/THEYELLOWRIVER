package com.example.management.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 14158
 */
@Data
@TableName("user_history")
public class ArticleHistory {
    private Long id;

    private Long articleId;

    private Long userId;}
