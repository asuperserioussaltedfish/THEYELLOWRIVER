package com.example.management.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 14158
 */
@Data
@TableName("suggestion")
public class SuggestVo {
    private Long id;

    private String suggestion;
    /**
     * 创建时间
     */
    private String createDate;
}
