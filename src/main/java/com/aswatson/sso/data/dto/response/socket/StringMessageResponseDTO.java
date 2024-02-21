package com.aswatson.sso.data.dto.response.socket;

import com.aswatson.sso.data.constant.SocketMessageActionConstants;
import jakarta.websocket.Session;

public class StringMessageResponseDTO {
    public int sender;
    public int target;
    public String message;
    public String msgType;

    public int getSender() {
        return sender;
    }

    public int getTarget() {
        return target;
    }

    public String getMessage() {
        return message;
    }

    public String getMsgType() {
        return msgType;
    }
}
