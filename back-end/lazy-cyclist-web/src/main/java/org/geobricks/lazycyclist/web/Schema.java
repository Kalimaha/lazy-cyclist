package org.geobricks.lazycyclist.web;

import javax.ws.rs.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Component
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Schema {

  private String schema;
  private String schemaPath;

  public Schema(Resource schemaPath) throws Exception {
    try                   { this.setSchemaPath(schemaPath.getFile().getPath()); }
    catch (IOException e) { throw new Exception(e.getMessage()); }
  }

  @GET
  public Response schema() {
    return Response.status(200)
                   .entity(this.schema)
                   .header("Access-Control-Allow-Origin", "*")
                   .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
  }

  public void init() throws IOException {
    String path = this.schemaPath + File.separator + "schema.json";
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    this.setSchema(new String(encoded, Charset.defaultCharset()));
  }

  private void setSchemaPath(String schemaPath) { this.schemaPath = schemaPath; }
  private void setSchema(String schema)         { this.schema = schema; }
}
