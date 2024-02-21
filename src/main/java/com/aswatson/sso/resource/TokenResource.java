package com.aswatson.sso.resource;

import com.aswatson.sso.data.dto.response.socket.StringMessageResponseDTO;
import com.aswatson.sso.service.SocketService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TokenResource {
    @Inject
    SocketService socketService;

    @POST
    @Path("/")
    public boolean sendMessage(StringMessageResponseDTO stringMessageResponseDTO) {
        socketService.sendMessage(stringMessageResponseDTO);
        return true;
    }

    @GET
    @Path("/signedUrl")
    public String getSignedUrl(@QueryParam("objectName") String objectName, @QueryParam("contentType") String contentType) throws IOException {
      return  socketService.getSignedUrl(objectName,contentType);
    }
}
