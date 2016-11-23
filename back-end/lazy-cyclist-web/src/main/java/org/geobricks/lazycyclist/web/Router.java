package org.geobricks.lazycyclist.web;

import org.geobricks.lazycyclist.core.Core;
import org.geobricks.lazycyclist.core.clients.DirectionsClient;
import org.geobricks.lazycyclist.core.clients.ElevationClient;
import org.geobricks.lazycyclist.core.models.Models.EnhancedElevationProfile;
import org.geobricks.lazycyclist.core.parsers.EEPParser;
import org.springframework.stereotype.Component;
import scala.collection.immutable.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/route")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Router {

  @GET
  @Path("/{from}/{to}")
  public Response route(@PathParam("from") String from, @PathParam("to") String to) {
    DirectionsClient dc = new DirectionsClient("AIzaSyDawUXHj6EEm9rdF52YGX102C9GTRyVZJs");
    ElevationClient  ec = new ElevationClient("AIzaSyDpqJYbyoCqtjZymDLC2mann1sJUzYObkI");

    List<EnhancedElevationProfile> profiles = Core.elevationProfile(from, to, dc, ec).right().get();
    String json = EEPParser.toJSON(profiles);

    return Response.status(200).entity(json).build();
  }
}
