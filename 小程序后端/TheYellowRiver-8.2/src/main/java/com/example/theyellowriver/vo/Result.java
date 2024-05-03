package com.example.theyellowriver.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 14158
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private Integer code;

    private String msg;

    private Object data;


    public static Result success(Object data) {
        return new Result(200,"success",data);
    }
    public static Result fail(Integer code, String msg) {
        return new Result(code,msg,null);
    }

}
