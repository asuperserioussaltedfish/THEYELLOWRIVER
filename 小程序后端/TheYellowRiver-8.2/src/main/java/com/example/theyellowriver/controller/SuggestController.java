package com.example.theyellowriver.controller;

import com.example.theyellowriver.dao.SuggestMapper;
import com.example.theyellowriver.pojo.Suggest;
import com.example.theyellowriver.vo.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 14158
 */
@RestController
@RequestMapping("suggest")
public class SuggestController {
    @Resource
    private SuggestMapper suggestMapper;
    @PostMapping()
    public Result suggest(@RequestBody Suggest suggest){
        suggest.setCreateDate(System.currentTimeMillis());
        return Result.success(suggestMapper.insert(suggest));
    }
}
