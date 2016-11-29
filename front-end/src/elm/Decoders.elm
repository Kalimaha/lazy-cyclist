module Decoders exposing (..)

import Models exposing (..)
import Json.Decode as Decode


decodeRoutes : Decode.Decoder String
decodeRoutes =
  Decode.string

elevationProfileListDecoder : Decode.Decoder (List ElevationProfile)
elevationProfileListDecoder =
  Decode.list elevationProfileDecoder

elevationProfileDecoder : Decode.Decoder ElevationProfile
elevationProfileDecoder =
  Decode.map3 ElevationProfile
    (Decode.field "totalDistance" Decode.float)
    (Decode.field "points" pointListDecoder)
    (Decode.field "climbs" climbListDecoder)

pointListDecoder : Decode.Decoder (List Point)
pointListDecoder =
  Decode.list pointDecoder

climbListDecoder : Decode.Decoder (List Climb)
climbListDecoder =
  Decode.list climbDecoder

climbDecoder : Decode.Decoder Climb
climbDecoder =
  Decode.map3 Climb
    (Decode.field "start" pointDecoder)
    (Decode.field "end"   pointDecoder)
    (Decode.field "slope" Decode.float)

pointDecoder : Decode.Decoder Point
pointDecoder =
  Decode.map2 Point 
    (Decode.field "x" Decode.float)
    (Decode.field "y" Decode.float)
