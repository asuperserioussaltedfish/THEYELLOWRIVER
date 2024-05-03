package com.example.theyellowriver.vo.params;

import lombok.Data;

/**
 * @author 14158
 */
@Data
public class CommentParam {

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}