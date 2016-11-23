package org.geobricks.lazycyclist.web;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestSchema extends JerseyTest {

  public TestSchema() {
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
  public void testSchema() {
    WebResource webResource = resource().path("/");
    ClientResponse response = webResource.get(ClientResponse.class);
    String responseBody     = response.getEntity(String.class);

    assertEquals(200, response.getStatus());
    assertNotNull(responseBody);
  }
}
