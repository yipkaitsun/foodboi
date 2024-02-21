package com.aswatson.sso;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.annotation.Priority;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Provider
@Priority(value = Priorities.ENTITY_CODER)
public class PathParamInterceptor implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String buId = requestContext.getUriInfo().getPathParameters().getFirst("buId");
        InputStream originalEntityStream = requestContext.getEntityStream();
        String requestBody = new String(originalEntityStream.readAllBytes(), StandardCharsets.UTF_8);
        String modifiedRequestBody = addKeyValuePairToJson(requestBody, buId);
        InputStream modifiedEntityStream = new ByteArrayInputStream(modifiedRequestBody.getBytes(StandardCharsets.UTF_8));
        requestContext.setEntityStream(modifiedEntityStream);
    }

    private String addKeyValuePairToJson(String json, String value) {
        Gson gson = new Gson();
        try {
            JsonObject jsonObject = json.isEmpty() ? new JsonObject() : gson.fromJson(json, JsonObject.class);
            jsonObject.addProperty("buId", value);
            return gson.toJson(jsonObject);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }
}