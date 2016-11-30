module Encoders exposing (..)

import Json.Encode as Encode
import Models exposing (..)


encodeClimbs : List Climb -> Encode.Value
encodeClimbs climbs =
  Encode.object
    [
      ("plotBands", Encode.list (List.map encodeClimb climbs))
    ]

encodeClimb : Climb -> Encode.Value
encodeClimb climb =
  Encode.object
    [
      ("from", Encode.float climb.start.x)
      , ("to", Encode.float climb.end.x)
      , ("color", Encode.string "#9B2335")
      , ("borderColor", Encode.string "#FFF")
      , ("borderWidth", Encode.int 1)
      , ("label", Encode.object [("text", Encode.string (toString (round climb.slope)))])
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

encodeSeries : List Point -> Encode.Value
encodeSeries points =
  Encode.object
    [
      ("data", (encodePoints points))
    ]

encodeHighchartsChart : HighchartsChart -> Encode.Value
encodeHighchartsChart chart =
  Encode.object
    [
      ("type", Encode.string chart.chartType)
      , ("zoomType", Encode.string chart.zoomType)
    ]

encodeHighchartsCredits : HighchartsCredits -> Encode.Value
encodeHighchartsCredits credits =
  Encode.object
    [
      ("enabled", Encode.bool credits.enabled)
    ]

encodeHighchartsLegend : HighchartsLegend -> Encode.Value
encodeHighchartsLegend legend =
  Encode.object
    [
      ("enabled", Encode.bool legend.enabled)
    ]

encodeHighchartsTitle : HighchartsTitle -> Encode.Value
encodeHighchartsTitle title =
  Encode.object
    [
      ("text", Encode.string title.text)
    ]

encodeHighchartsYAxis : HighchartsYAxis -> Encode.Value
encodeHighchartsYAxis yAxis =
  Encode.object
    [
      ("gridLineWidth", Encode.int 0)
      , ("title", Encode.object [("text", Encode.string yAxis.text)])
    ]

encodePlotOptions : Encode.Value
encodePlotOptions =
  Encode.object
    [
      ("series", Encode.object [
        ("fillColor", Encode.string "#009B77")
        , ("color", Encode.string "#000")
      ])
      , ("areaspline", Encode.object [
        ("marker", Encode.object [("enabled", Encode.bool False)])
        , ("fillOpacity", Encode.int 1)
        , ("enableMouseTracking", Encode.bool False)
      ])
    ]

encodeHighcharts : Highcharts -> Encode.Value
encodeHighcharts chart =
  Encode.object [
    ("chart", encodeHighchartsChart chart.chart)
    , ("credits", encodeHighchartsCredits chart.credits)
    , ("title", encodeHighchartsTitle chart.title)
    , ("subtitle", encodeHighchartsTitle chart.subtitle)
    , ("yAxis", encodeHighchartsYAxis chart.yAxis)
    , ("series", Encode.list [encodeSeries chart.points])
    , ("legend", encodeHighchartsLegend chart.legend)
    , ("xAxis", encodeClimbs chart.climbs)
    , ("plotOptions", encodePlotOptions)
  ]
