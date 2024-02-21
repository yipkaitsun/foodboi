package com.aswatson.sso.resource.manager;

import io.smallrye.common.constraint.Nullable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class SessionManager {

    private final Map<String, Session> sessionsMap = new ConcurrentHashMap<>();

    public void setSession(int id, Session session) {
        this.sessionsMap.put(String.valueOf(id), session);
    }

    public void removeSession(String key) {
        this.sessionsMap.remove(key);
    }

    @Nullable
    public Session getSession(String key) {
        return this.sessionsMap.get(key);
    }
}
