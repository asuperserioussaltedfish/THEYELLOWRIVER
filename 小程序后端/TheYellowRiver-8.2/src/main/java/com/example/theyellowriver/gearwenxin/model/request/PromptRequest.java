package com.example.theyellowriver.gearwenxin.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Ge Mingjia
 * @date 2023/7/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromptRequest {

    /**
     * prompt工程里面对应的模板id
     */
    private String id;

    /**
     * 参数map
     */
    private Map<String, String> paramMap;

}
