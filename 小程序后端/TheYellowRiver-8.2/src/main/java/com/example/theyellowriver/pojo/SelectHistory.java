package com.example.theyellowriver.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 14158
 */
@Data
@TableName("search_history")
public class SelectHistory {
    private Long id;

    private String content;

    private Long userId;}
