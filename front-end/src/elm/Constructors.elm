module Constructors exposing (..)

import Models exposing (..)

highcharts : String -> String -> String -> (List Point) -> (List Climb) -> Highcharts
highcharts title subtitle yAxisText points climbs =
  {
    chart = highchartsChart,
    credits = highchartsCredits,
    title = highchartsTitle title,
    subtitle = highchartsTitle subtitle,
    yAxis = highchartsYAxis yAxisText,
    points = points,
    legend = highchartsLegend,
    climbs = climbs
  }

highchartsChart : HighchartsChart
highchartsChart =
  {
    chartType = "areaspline",
    zoomType = "xy"
  }

highchartsCredits : HighchartsCredits
highchartsCredits =
  {
    enabled = False
  }

highchartsLegend : HighchartsLegend
highchartsLegend =
  {
    enabled = False
  }

highchartsTitle : String -> HighchartsTitle
highchartsTitle text =
  {
    text = text
  }

highchartsYAxis : String -> HighchartsYAxis
highchartsYAxis text =
  {
    text = text
  }
