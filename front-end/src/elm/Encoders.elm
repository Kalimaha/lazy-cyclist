module Encoders exposing (..)

import Json.Encode as Encode
import Models exposing (..)


encodeClimbs : List Climb -> String
encodeClimbs climbs =
  String.concat [
    """"xAxis": { """
    , """"plotBands": ["""
    , String.join "," (List.map encodeClimb climbs)
    , """]}"""
  ]

encodeClimb : Climb -> String
encodeClimb climb =
  String.concat [
    """{"""
    , """ "from": """ ++ toString (climb.start.x) ++ ""","""
    , """"to": """ ++ toString (climb.end.x) ++ ""","""
    , """ "color": "#CA1616" """
    , """}"""
  ]

encodePoint : Point -> Encode.Value
encodePoint point =
  Encode.list [Encode.float point.x, Encode.float point.y]

encodePoints : List Point -> Encode.Value
encodePoints points =
  Encode.list (List.map encodePoint points)
