package com.example.theyellowriver.controller;

import com.example.theyellowriver.handler.Fish;
import com.example.theyellowriver.service.TagService;
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
@RequestMapping("/tags")
public class TagsController {
    @Resource
    private TagService tagService;
    @Fish
    @GetMapping("hot")
    public Result hot(){
        int limit=5;
        return tagService.hots(limit);
    }
    @Fish
    @GetMapping
    public Result findAll(){
        return Result.success(tagService.findAll());
    }
    @Fish
    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }
    @Fish
    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }
}
