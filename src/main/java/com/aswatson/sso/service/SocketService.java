package com.aswatson.sso.service;

import com.aswatson.sso.data.dto.request.socket.StringSocketRequestDTO;
import com.aswatson.sso.data.dto.response.socket.BaseSocketResponseDTO;
import com.aswatson.sso.data.dto.response.socket.StringMessageResponseDTO;
import com.aswatson.sso.data.model.SocketMessage;
import com.aswatson.sso.exception.socket.SocketException;
import com.aswatson.sso.resource.manager.SessionManager;
import com.google.cloud.storage.*;
import com.google.gson.Gson;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.aswatson.sso.data.constant.SocketMessageActionConstants.HAND_SHAKE;

@ApplicationScoped
public class SocketService {

    protected static final Logger LOGGER = Logger.getLogger(SocketService.class);

    @Inject
    SessionManager sessionManager;
    @Inject
    Storage storage;

    class MessageHandler implements SocketMessage.ActionHandler {
        @Override
        public void handle(Session session, String action, Object data) {
            LOGGER.debug("Message Ation:" + action + "," + "Data:" + data);
            if (action.equals("handShake")) {
                int id = ((Double) data).intValue();
                sessionManager.setSession(id, session);
            }
        }
    }

    public String getSignedUrl(String objectName,String contentType) {
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of("hmd-d0d5b.appspot.com", objectName)).setContentType(contentType).build();
        return storage.signUrl(
                blobInfo,
                15,
                TimeUnit.MINUTES,
                Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
                Storage.SignUrlOption.withContentType()).toString();
    }

    public void sendMessage(StringMessageResponseDTO message) {
        Session targetSession = sessionManager.getSession(String.valueOf(message.getTarget()));
        if (targetSession != null) {
            new StringSocketRequestDTO(message.getMessage(), message.getSender(), message.getMsgType()).send(targetSession, "message");
//            new StringSocketRequestDTO("",stringMessageDto.getSender(),"image").send(targetSession, "message");
        }
    }

    private final MessageHandler messageHandler = new MessageHandler();
    private final Gson gson = new Gson();

    public void handleMessage(Session session, String message) {
        gson.fromJson(message, SocketMessage.class).actionHandler(session, messageHandler);
    }

    public void handleConnect(Session session) {
        new BaseSocketResponseDTO().send(session, HAND_SHAKE);
    }

    private void handleException(Throwable throwable, Session session) {
        Throwable exception = throwable instanceof SocketException ? throwable : new SocketException();
        try {
            CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, exception.getMessage());
            session.close(closeReason);
        } catch (IOException ex) {
            LOGGER.debug("sessionId:" + session.getId() + ":\n" + ex.getMessage());
        }
    }
}