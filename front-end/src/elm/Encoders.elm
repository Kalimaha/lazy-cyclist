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
    , """"color": "#9B2335", """
    , """"borderWidth": 1, """
    , """"borderColor": "#FFF", """
    , """"label": { "text": " """ ++ toString (round climb.slope) ++ """%", "style": { "color": "#fff" } } """
    , """}"""
  ]

round : Float -> Float
round f =
  let tenTimes =
    f * 10
  in
    (toFloat (Basics.round(tenTimes))) / 10

encodePoint : Point -> Encode.Value
encodePoint point =
  Encode.list [Encode.float point.x, Encode.float point.y]

encodePoints : List Point -> Encode.Value
encodePoints points =
  Encode.list (List.map encodePoint points)
