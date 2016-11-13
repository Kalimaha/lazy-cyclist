package org.geobricks.lazycyclist.core.clients

import org.scalatest.{BeforeAndAfter, FunSpec}
import fr.simply.util.ContentType
import fr.simply.{GET, StaticServerResponse, StubServer}

class TestGoogleAPIClient extends FunSpec with BeforeAndAfter {
  class TestClient extends GoogleAPIClient

  describe(".request") {
    val client = new TestClient

    describe("when the request succeeds") {
      val content = "{\"hello\": \"world\"}"
      val route = GET (
        path = "/directions",
        response = StaticServerResponse(ContentType("application/json"), content, 200)
      )
      val server = new StubServer(8080, route)

      it("returns JSON content") {
        server.start
        assert(client.request("http://localhost:8080/directions") == Right(content))
        server.stop
      }
    }

    describe("when there is an internal server error") {
      val content = "Internal server error."
      val route = GET (
        path = "/directions",
        response = StaticServerResponse(ContentType("application/json"), content, 500)
      )
      val server = new StubServer(8080, route)

      it("returns an error") {
        server.start
        assert(client.request("http://localhost:8080/directions") == Left(content))
        server.stop
      }
    }

    describe("when there is a client error") {
      val content = "Client error."
      val route = GET (
        path = "/directions",
        response = StaticServerResponse(ContentType("application/json"), content, 400)
      )
      val server = new StubServer(8080, route)

      it("returns an error") {
        server.start
        assert(client.request("http://localhost:8080/directions") == Left(content))
        server.stop
      }
    }
  }
}