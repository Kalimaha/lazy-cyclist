package org.geobricks.lazycyclist.web;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Schema {

  @GET
  @Path("/")
  public Response test() {
    Gson g = new Gson();
    return Response.status(200).entity(g.toJson("Hallo, world!")).build();
  }
}
