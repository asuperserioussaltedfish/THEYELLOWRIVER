package com.example.theyellowriver.controller;

import com.example.theyellowriver.pojo.User;
import com.example.theyellowriver.utils.BaseResponse;
import com.example.theyellowriver.utils.UserThreadLocal;
import com.gearwenxin.client.ErnieBotClient;
import com.gearwenxin.model.response.ChatResponse;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;



/**
 * @author 14158
 */

@RestController
public class ChatController {

    // 要调用的模型的客户端

    @Resource
    private ErnieBotClient ernieBotClient;

    /**单次对话
     *
     * @param msg 消息
     * @return 结果
     */
    @PostMapping("/chat")
    public BaseResponse<String> chatSingle(String msg) {
        ChatResponse response = ernieBotClient.chatSingle(msg);
        return BaseResponse.success(response.getResult());
    }

    /** 连续对话
     *
     * @param msg 消息
     * @return 结果
     */



    @PostMapping("/chats")
    public BaseResponse<String> chatCont(String msg) {
        User user = UserThreadLocal.get();
        ChatResponse response = ernieBotClient.chatCont(msg, user.getId().toString());
        return BaseResponse.success(response.getResult());
    }
//
//    /**流式返回,单次对话
//     *
//     * @param msg 消息
//     * @return 结果
//     */
//    @PostMapping(value = "/stream/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public BaseResponse<Flux<ChatResponse>> chatSingleStream(String msg) {
//        Flux<ChatResponse> chatResponseFlux = ernieBotClient.chatSingleOfStream(msg);
//        return BaseResponse.success(chatResponseFlux);
//    }
//
//    /**流式返回,连续对话
//     *
//     * @param msg 消息
//     * @return 结果
//     */
//    @PostMapping(value = "/stream/chats", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public BaseResponse<Flux<ChatResponse>> chatContStream(String msg) {
//        User user = UserThreadLocal.get();
//        Flux<ChatResponse> chatResponseFlux = ernieBotClient.chatContOfStream(msg, user.getId().toString());
//        return BaseResponse.success(chatResponseFlux);
//    }

}

