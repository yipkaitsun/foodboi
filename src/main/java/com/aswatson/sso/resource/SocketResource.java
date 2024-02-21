package com.aswatson.sso.resource;

import com.aswatson.sso.resource.manager.SessionManager;
import com.aswatson.sso.service.SocketService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/")
@ApplicationScoped
public class SocketResource {

    @Inject
    SessionManager sessionManager;

    @Inject
    SocketService socketService;


    @OnOpen
    public void onOpen(Session session) {
                socketService.handleConnect(session);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
                socketService.handleMessage(session, message);
    }

    @OnClose
    public void onClose(Session session) {
        sessionManager.removeSession(session.getId());
    }
}
