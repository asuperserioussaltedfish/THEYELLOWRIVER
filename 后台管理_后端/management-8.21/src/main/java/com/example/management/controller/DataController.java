package com.example.management.controller;

import com.example.management.dao.ArticleMapper;
import com.example.management.dao.SuggestMapper;
import com.example.management.dao.UserMapper;
import com.example.management.handler.Fish;
import com.example.management.pojo.Data;
import com.example.management.vo.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 14158
 */
@RestController
@CrossOrigin(origins = "http://localhost:9528")
@RequestMapping("/prod-api/api/data")
public class DataController {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private SuggestMapper suggestMapper;
    @PostMapping()
    public Result data() {
        Data data = new Data();
        data.setAllArticle(articleMapper.numberOfArticles());
        data.setAllUser(userMapper.numberOfUser());
        data.setAllSuggestion(suggestMapper.numberOfSuggestion());
        data.setNewArticle(articleMapper.newArticles());
        data.setNewUser(userMapper.newUser());
        data.setNewSuggestion(suggestMapper.newSuggestion());
        return Result.success(data);

    }
}
