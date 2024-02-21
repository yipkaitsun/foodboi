package com.aswatson.sso.data.dto.request.socket;

import com.aswatson.sso.data.model.SocketMessage;
import jakarta.websocket.Session;

public class StringSocketRequestDTO extends BaseSocketRequestDTO {
    private final String message;
    private final int senderId;
    private final String msgType;

    public StringSocketRequestDTO(String message,int id,String msgType) {
        this.message = message;
        this.senderId=id;
        this.msgType = msgType;
    }

    public void send(Session session, String message) {
        SocketMessage<StringSocketRequestDTO> socketMessage = new SocketMessage<>("message", this);
        session.getAsyncRemote().sendText(socketMessage.toJson());
    }
}
