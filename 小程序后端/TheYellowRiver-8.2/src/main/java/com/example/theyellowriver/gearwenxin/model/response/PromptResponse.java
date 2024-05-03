package com.example.theyellowriver.gearwenxin.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gearwenxin.model.response.PromptErrMessage;
import com.gearwenxin.model.response.PromptResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ge Mingjia
 * @date 2023/7/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromptResponse {

    @JsonProperty("log_id")
    private String logId;

    private PromptResult result;

    private Integer status;

    private Boolean success;

    @JsonProperty("error_code")
    private Integer errorCode;

    @JsonProperty("error_msg")
    private String errorMsg;

    @JsonProperty("code")
    private String promptErrCode;

    @JsonProperty("message")
    private PromptErrMessage promptErrMessage;
}
