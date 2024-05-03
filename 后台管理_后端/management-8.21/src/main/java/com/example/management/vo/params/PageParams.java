package com.example.management.vo.params;

import lombok.Data;

/**
 * @author 14158
 */
@Data
public class PageParams {
    /**
     * 文章页数
     */
    private int page = 1;
    /**
     * 每页显示的文章数量
     */
    private int pageSize = 10;
    /**
     * 类别编号
     */
    private Long categoryId;
    /**
     * 标签编号
     */
    private Long tagId;
    /**
     * 年份
     */
    private String year;
    /**
     * 月份
     */
    private String month;

    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }
}
