package com.aswatson.sso.data.model;

import com.google.gson.Gson;
import jakarta.websocket.Session;

public class SocketMessage<T> {
    public interface ActionHandler {
        void handle(Session session, String action, Object data);
    }

    public SocketMessage(String action, T data) {
        this.action = action;
        this.data = data;
    }

    private final String action;
    private final T data;

    public void actionHandler(Session session, ActionHandler actionHandler) {
        actionHandler.handle(session, action, this.data);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
