package com.example.theyellowriver.service;

import com.example.theyellowriver.vo.CategoryVo;
import com.example.theyellowriver.vo.Result;

/**
 * @author 14158
 */
public interface CategoryService {
    /**
     * 通过id查询分类
     * @param id id
     * @return CategoryVo
     */
    CategoryVo findCategoryById(Long id);

    /**
     *  查找所有
     * @return Result
     */
    Result findAll();

    /**
     * 查找所有标签
     * @return Result
     */
    Result findAllDetail();

    /**
     * 通过id查询分类
     * @param id id
     * @return Result
     */
    Result categoriesDetailById(Long id);
}