module Models exposing (..)

type alias Model = {
  from: String,
  to: String
}

type alias Chart = {
  id: String,
  payload: String
}

type alias ElevationProfile = {
  totalDistance: Float,
  points: (List Point),
  climbs: (List Climb)
}

type alias Climb = {
  start:  Point,
  end:    Point,
  slope:  Float
}

type alias Point = {
  x: Float,
  y: Float
}
