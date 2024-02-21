package com.aswatson.sso.data.dto.response.socket;

import com.aswatson.sso.data.constant.SocketMessageActionConstants;
import jakarta.websocket.Session;

public class AuthenticationCodeResponseDTO extends BaseSocketResponseDTO {
    private final String authenticationCode;

    public AuthenticationCodeResponseDTO(String authenticationCode) {
        this.authenticationCode = authenticationCode;
    }

    public void send(Session session) {
        super.send(session, SocketMessageActionConstants.AUTHENTICATION_CODE);
    }
}
