package com.example.management.controller;

import com.example.management.dao.CommentMapper;
import com.example.management.query.Id;
import com.example.management.service.CommentsService;
import com.example.management.vo.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.ObjectUtils.isEmpty;


/**
 * @author 14158
 */

@RestController
@RequestMapping("/prod-api/comments")
@CrossOrigin(origins = "http://localhost:9528")
public class CommentsController {
    @Resource
    private CommentsService commentsService;
    @Resource
    private CommentMapper commentMapper;
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long articleId){
        return commentsService.commentsByArticleId(articleId);
    }
    @PostMapping("deleteComment")
    public Result deleteComment(@RequestBody Id id){
        if (isEmpty(commentMapper.selectById(id.getId()))){
            return Result.fail(50001,"未找到该评论");
        }
        commentMapper.deleteById(id.getId());
        return Result.success(null);
    }
}