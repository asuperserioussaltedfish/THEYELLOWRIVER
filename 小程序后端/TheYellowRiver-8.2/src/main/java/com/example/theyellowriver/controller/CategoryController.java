package com.example.theyellowriver.controller;

import com.example.theyellowriver.handler.Fish;
import com.example.theyellowriver.service.CategoryService;
import com.example.theyellowriver.vo.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 14158
 */
@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Resource
    private CategoryService categoryService;
    @Fish
    @GetMapping
    public Result listCategory() {
        return categoryService.findAll();
    }
    @GetMapping("detail")
    @Fish
    public Result categoriesDetail(){
        return categoryService.findAllDetail();
    }
    @GetMapping("detail/{id}")
    @Fish
    public Result categoriesDetailById(@PathVariable("id") Long id){
        return categoryService.categoriesDetailById(id);
    }
}