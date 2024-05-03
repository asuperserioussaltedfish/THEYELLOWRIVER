package com.example.management.vo.params;

import com.example.management.vo.CategoryVo;
import com.example.management.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @author 14158
 */
@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;
    private List<TagVo> tags;

    private String title;
}