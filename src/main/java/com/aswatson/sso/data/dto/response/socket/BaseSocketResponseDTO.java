package com.aswatson.sso.data.dto.response.socket;

import com.aswatson.sso.data.model.SocketMessage;
import jakarta.websocket.Session;

public class BaseSocketResponseDTO {
    public void send(Session session, String action) {
        SocketMessage<BaseSocketResponseDTO> socketMessage = new SocketMessage<>(action, this);
        session.getAsyncRemote().sendText(socketMessage.toJson());
    }
}
