package org.geobricks.lazycyclist.core

import org.geobricks.lazycyclist.core.Core._
import org.scalatest.FunSpec
import fr.simply.util.ContentType
import fr.simply.{GET, StaticServerResponse, StubServer}

class TestCore extends FunSpec {
  describe(".directions") {
    val url = "http://localhost:8080/directions"

    describe("when the request succeeds") {
      val content = "{\"hello\": \"world\"}"
      val route = GET (
        path = "/directions",
        response = StaticServerResponse(ContentType("application/json"), content, 200)
      )
      val server = new StubServer(8080, route)

      it("returns JSON content") {
        server.start
        assert(directions("http://localhost:8080/directions") == Right(content))
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
        assert(directions("http://localhost:8080/directions") == Left(content))
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
        assert(directions("http://localhost:8080/directions") == Left(content))
        server.stop
      }
    }
  }

  describe(".directionsURL") {
    describe("when the API key is valid") {
      it("creates the request URL for the Google Directions API") {
        assert(directionsURL("Home", "Work", "ABC") == Right("https://maps.googleapis.com/maps/api/directions/json?origin=Home&destination=Work&key=ABC"))
      }
    }
    describe("when the API key is not valid") {
      describe("when the API key is null") {
        it("returns an error") {
          assert(directionsURL("Home", "Work", null) == Left("Parameter 'directionsAPIKey' can't be null."))
        }
      }

      describe("when the API key is empty") {
        it("returns an error") {
          assert(directionsURL("Home", "Work", "") == Left("Parameter 'directionsAPIKey' can't be empty."))
        }
      }
    }
  }

  describe(".encode") {
    it("encodes a string for the API request") {
      assert(encode("75 9th Ave, New York, NY") == "75+9th+Ave,+New+York,+NY")
    }
  }

  describe(".validate") {
    describe("when inputs are valid") {
      it("returns true") {
        assert(validate("Home", "Work") == Right(true))
      }
    }

    describe("when 'from' is empty") {
      it("returns an error") {
        assert(validate("", "Work") == Left("Parameter 'from' can't be empty."))
      }
    }

    describe("when 'from' is null") {
      it("returns an error") {
        assert(validate(null, "Work") == Left("Parameter 'from' can't be null."))
      }
    }

    describe("when 'to' is empty") {
      it("returns an error") {
        assert(validate("Home", "") == Left("Parameter 'to' can't be empty."))
      }
    }

    describe("when 'to' is null") {
      it("returns an error") {
        assert(validate("Home", null) == Left("Parameter 'to' can't be null."))
      }
    }
  }
}
