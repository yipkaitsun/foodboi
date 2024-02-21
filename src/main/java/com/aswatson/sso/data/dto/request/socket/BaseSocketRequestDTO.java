package com.aswatson.sso.data.dto.request.socket;

import com.aswatson.sso.data.model.SocketMessage;
import jakarta.websocket.Session;

public class BaseSocketRequestDTO {
    public void send(Session session, String action) {
        SocketMessage<BaseSocketRequestDTO> socketMessage = new SocketMessage<>(action, this);
        session.getAsyncRemote().sendText(socketMessage.toJson());
    }
}
