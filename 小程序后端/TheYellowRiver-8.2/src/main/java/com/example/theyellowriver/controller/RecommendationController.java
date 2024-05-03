package com.example.theyellowriver.controller;

import com.example.theyellowriver.handler.Fish;
import com.example.theyellowriver.service.RecommendationService;
import com.example.theyellowriver.vo.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 个性化推荐
 * @author 14158
 */
@RestController
@RequestMapping("recommendation")
public class RecommendationController {
    @Resource
     private RecommendationService recommendationService;
    @Fish
    @PostMapping()
    public Result recommendation(HttpServletRequest request) throws TasteException {
        return Result.success(recommendationService.generateRecommendations(request));
    }
}
