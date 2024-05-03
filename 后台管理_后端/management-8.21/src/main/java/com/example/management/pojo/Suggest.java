package com.example.management.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 14158
 */
@Data
@TableName("suggestion")
public class Suggest {
    private Long id;

    private String suggestion;
    /**
     * 创建时间
     */
    private Long createDate;
}
