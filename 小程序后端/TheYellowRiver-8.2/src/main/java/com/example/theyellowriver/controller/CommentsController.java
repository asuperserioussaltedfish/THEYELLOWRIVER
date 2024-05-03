package com.example.theyellowriver.controller;

import com.example.theyellowriver.handler.Fish;
import com.example.theyellowriver.pojo.User;
import com.example.theyellowriver.service.CommentsService;
import com.example.theyellowriver.utils.UserThreadLocal;
import com.example.theyellowriver.vo.Result;
import com.example.theyellowriver.vo.params.CommentParam;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 14158
 */

@RestController
@RequestMapping("comments")
public class CommentsController {
    @Resource
    private CommentsService commentsService;
    @Fish
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long articleId){
        return commentsService.commentsByArticleId(articleId);
    }
    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        User user = UserThreadLocal.get();

        List<Object> list =new ArrayList<>();
        list.add(commentsService.comment(commentParam));
        list.add(user.getNickname());
        return Result.success(list);
    }
}