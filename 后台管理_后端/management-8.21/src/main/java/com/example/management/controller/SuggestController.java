package com.example.management.controller;

import com.example.management.dao.SuggestMapper;
import com.example.management.pojo.Suggest;
import com.example.management.pojo.SuggestVo;
import com.example.management.vo.Result;
import jakarta.annotation.Resource;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 14158
 */
@RestController
@RequestMapping("/prod-api/suggest")
@CrossOrigin(origins = "http://localhost:9528")
public class SuggestController {
    @Resource
    private SuggestMapper suggestMapper;
    @GetMapping
    public Result findAll(){
        return Result.success(copyList(suggestMapper.allSuggest()));
    }
    private List<SuggestVo> copyList(List<Suggest> records) {
        List<SuggestVo> suggestVoList = new ArrayList<>();
        for (Suggest record : records) {
            suggestVoList.add(copy(record));
        }
        return suggestVoList;
    }
    private SuggestVo copy(Suggest suggest){
        SuggestVo suggestVo = new SuggestVo();
        //copy一下，把文章的东西copy到文章vo里
        try{
            BeanUtils.copyProperties(suggest,suggestVo);
        }catch (Exception e){
            System.out.println("错误原因"+e);
        }
        //设置一下时间
        suggestVo.setCreateDate(new DateTime(suggest.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        return suggestVo;
    }
}
