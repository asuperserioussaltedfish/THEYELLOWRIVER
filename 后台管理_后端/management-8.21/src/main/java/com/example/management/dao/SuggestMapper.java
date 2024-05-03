package com.example.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.management.pojo.Suggest;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 14158
 */
public interface SuggestMapper extends BaseMapper<Suggest> {
    /**
     * 反馈总数量
     * @return integer
     */
    @Select("select count(*) as allSuggestion from suggestion")
    Integer numberOfSuggestion();
    /**
     * 当日新增反馈数量
     * @return int
     */
    @Select("SELECT COUNT(*) AS new_suggestion_count FROM suggestion " +
            "WHERE DATE(FROM_UNIXTIME(round(create_date/1000,0))) = CURDATE()")
    Integer newSuggestion();

    /**
     * 所有反馈意见
     * @return List
     */
    @Select("select * from suggestion order by create_date desc ")
    List<Suggest> allSuggest();
}
