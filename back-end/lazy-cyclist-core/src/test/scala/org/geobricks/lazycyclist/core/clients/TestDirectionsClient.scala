package org.geobricks.lazycyclist.core.clients

import org.scalatest.{BeforeAndAfter, FunSpec}

class TestDirectionsClient extends FunSpec with BeforeAndAfter {
  var client: DirectionsClient = _

  before {
    client = new DirectionsClient(apiKey = "abc123")
  }

  describe(".directionsURL") {
    describe("when the API key is valid") {
      it("creates the request URL for the Google Directions API") {
        assert(client.directionsURL("Home", "Work") == Right("https://maps.googleapis.com/maps/api/directions/json?origin=Home&destination=Work&key=abc123&mode=bicycling&alternatives=true&avoid=ferries"))
      }
    }
    describe("when the API key is not valid") {
      describe("when the API key is null") {
        it("returns an error") {
          assert(new DirectionsClient(apiKey = null).directionsURL("Home", "Work") == Left("Parameter 'directionsAPIKey' can't be null."))
        }
      }

      describe("when the API key is empty") {
        it("returns an error") {
          assert(new DirectionsClient(apiKey = "").directionsURL("Home", "Work") == Left("Parameter 'directionsAPIKey' can't be empty."))
        }
      }
    }
  }

  describe(".encode") {
    it("encodes a string for the API request") {
      assert(client.encode("75 9th Ave, New York, NY") == "75+9th+Ave,+New+York,+NY")
    }
  }

  describe(".validate") {
    describe("when inputs are valid") {
      it("returns true") {
        assert(DirectionsClient.validate("Home", "Work") == Right(true))
      }
    }

    describe("when 'from' is empty") {
      it("returns an error") {
        assert(DirectionsClient.validate("", "Work") == Left("Parameter 'from' can't be empty."))
      }
    }

    describe("when 'from' is null") {
      it("returns an error") {
        assert(DirectionsClient.validate(null, "Work") == Left("Parameter 'from' can't be null."))
      }
    }

    describe("when 'to' is empty") {
      it("returns an error") {
        assert(DirectionsClient.validate("Home", "") == Left("Parameter 'to' can't be empty."))
      }
    }

    describe("when 'to' is null") {
      it("returns an error") {
        assert(DirectionsClient.validate("Home", null) == Left("Parameter 'to' can't be null."))
      }
    }
  }
}