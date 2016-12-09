module Models exposing (..)

type alias Model = {
  from: String,
  to: String,
  state: State
}

type State
  = Idle
  | Loading

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

type alias Highcharts = {
  chart: HighchartsChart,
  credits: HighchartsCredits,
  title: HighchartsTitle,
  subtitle: HighchartsTitle,
  yAxis: HighchartsYAxis,
  points: (List Point),
  legend: HighchartsLegend,
  climbs: (List Climb)
}

type alias HighchartsChart = {
  chartType: String,
  zoomType: String
}

type alias HighchartsTitle = {
  text: String
}

type alias HighchartsCredits = {
  enabled: Bool
}

type alias HighchartsLegend = {
  enabled: Bool
}

type alias HighchartsYAxis = {
  text: String
}
