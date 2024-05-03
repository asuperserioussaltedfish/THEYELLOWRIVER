package com.example.management.controller;

import com.example.management.service.TagService;
import com.example.management.vo.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 14158
 */
@RestController
@RequestMapping("/prod-api/tags")
public class TagsController {
    @Resource
    private TagService tagService;
    @GetMapping("hot")
    public Result hot(){
        int limit=5;
        return tagService.hots(limit);
    }
    @GetMapping
    public Result findAll(){
        return Result.success(tagService.findAll());
    }
    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }
    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }
}
