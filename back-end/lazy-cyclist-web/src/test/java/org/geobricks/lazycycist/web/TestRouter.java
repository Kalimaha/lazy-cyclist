package org.geobricks.lazycycist.web;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.geobricks.lazycyclist.core.clients.DirectionsClient;
import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import scala.util.Either;
import scala.util.Right;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestRouter extends JerseyTest {

  public TestRouter() {
    super(
      new WebAppDescriptor.Builder("org.geobricks.lazycyclist.web")
        .contextPath("testing")
        .contextParam("contextConfigLocation", "classpath:testApplicationContext.xml")
        .contextListenerClass(ContextLoaderListener.class)
        .servletClass(SpringServlet.class)
        .requestListenerClass(RequestContextListener.class)
        .build()
    );
  }

  @Test
  public void testRoute() {
    WebResource webResource = resource().path("/route/Federation+Square,+Melbourne,+Australia/511+Church+St,+Melbourne,+Australia");
    ClientResponse response = webResource.get(ClientResponse.class);
    String responseBody     = response.getEntity(String.class);

    assertEquals(200, response.getStatus());
    assertNotNull(responseBody);
  }
}
