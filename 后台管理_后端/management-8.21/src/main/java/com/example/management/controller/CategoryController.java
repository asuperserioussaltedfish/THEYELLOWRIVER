package com.example.management.controller;

import com.example.management.service.CategoryService;
import com.example.management.vo.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author 14158
 */
@RestController
@RequestMapping("/prod-api/categorys")
@CrossOrigin(origins = "http://localhost:9528")
public class CategoryController {

    @Resource
    private CategoryService categoryService;
    @GetMapping
    public Result listCategory() {
        return categoryService.findAll();
    }
    @GetMapping("detail")
    public Result categoriesDetail(){
        return categoryService.findAllDetail();
    }
    @GetMapping("detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") Long id){
        return categoryService.categoriesDetailById(id);
    }
}