package com.example.theyellowriver.gearwenxin.common;

import com.gearwenxin.common.ErrorCode;
import com.gearwenxin.common.RoleEnum;
import com.gearwenxin.exception.BusinessException;
import com.gearwenxin.model.Message;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Ge Mingjia
 * @date 2023/7/23
 */
public class WenXinUtils {

    public static final int MAX_TOTAL_LENGTH = 2000;

    public static Queue<Message> buildUserMessageQueue(String content) {
        Queue<Message> messageQueue = new LinkedList<>();
        Message message = buildUserMessage(content);
        messageQueue.offer(message);
        return messageQueue;
    }

    public static Queue<Message> buildMessageQueue(Message userMessage, Message assistantMessage) {
        Queue<Message> messageQueue = new LinkedList<>();
        offerMessage(messageQueue, userMessage);
        offerMessage(messageQueue, assistantMessage);
        return messageQueue;
    }

    public static Message buildUserMessage(String content) {
        return new Message(com.gearwenxin.common.RoleEnum.user, content);
    }

    public static Message buildAssistantMessage(String content) {
        return new Message(com.gearwenxin.common.RoleEnum.assistant, content);
    }

    /**
     * 向历史消息中添加消息
     *
     * @param messagesHistory 历史消息队列
     * @param message         需添加的Message
     */
    public static void offerMessage(Queue<Message> messagesHistory, Message message) {

        if (messagesHistory == null || message == null || StringUtils.isEmpty(message.getContent())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        messagesHistory.offer(message);
        if (message.getRole() == com.gearwenxin.common.RoleEnum.assistant) {
            return;
        }

        int totalLength = 0;
        for (Message msg : messagesHistory) {
            if (msg.getRole() == RoleEnum.user) {
                totalLength += msg.getContent().length();
            }
        }
        while (totalLength > MAX_TOTAL_LENGTH) {
            Message firstMessage = messagesHistory.poll();
            Message secondMessage = messagesHistory.poll();
            if (firstMessage != null && secondMessage != null) {
                totalLength -= (firstMessage.getContent().length() + secondMessage.getContent().length());
            }
        }
    }

}
