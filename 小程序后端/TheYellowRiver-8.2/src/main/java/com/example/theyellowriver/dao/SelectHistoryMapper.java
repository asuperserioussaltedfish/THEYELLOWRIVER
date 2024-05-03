package com.example.theyellowriver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.theyellowriver.pojo.SelectHistory;

import java.util.List;

/**
 * @author 14158
 */
public interface SelectHistoryMapper extends BaseMapper<SelectHistory> {
    /**
     * 查询历史记录
     * @param id 用户id
     * @return List<String>
     */
    List<String> selectContent(Long id);
}
