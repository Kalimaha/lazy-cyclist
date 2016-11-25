port module Main exposing (..)

import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (onInput, onClick)
import Http
import Json.Decode as Decode
import Json.Encode as Encode

type alias Model = {
  from: String,
  to: String
}

init : ( Model, Cmd Msg )
init =
  (
    {
      from = "Federation Square, Melbourne, Australia",
      to   = "511 Church St, Melbourne, Australia"
    },
    Cmd.none
  )

type Msg
  = From String
  | To String
  | Submit
  | Routes (Result Http.Error (List ElevationProfile))

view : Model -> Html Msg
view model =
  div [] [
    div [ class "row" ] [
      div [ class "col-lg-12" ] [
        div [ class "form-group" ] [
          label [] [
            i [ class "fa fa-home" ] []
            , text " From"
          ]
          , input [
              class "form-control input-lg"
              , type_ "text"
              , placeholder "e.g. Federation Square, Melbourne, Australia"
              , onInput From
              , value model.from
            ] []
          , br [] []
          , label [] [
            i [ class "fa fa-map-marker" ] []
            , text " To"
          ]
          , input [
              class "form-control input-lg"
              , type_ "text"
              , placeholder "e.g. 511 Church St, Melbourne, Australia"
              , onInput To
              , value model.to
            ] []
          , br [] []
          , button [
            class "btn btn-lg btn-primary"
            , onClick Submit
            ] [
            i [ class "fa fa-bicycle" ] []
            , text " Route!"
          ]
        ]
      ]
    ]
    , hr [] []
    , div [ class "row" ] [
        div [ class "col-lg-12" ] [
          div [ id "chart_0" ] [ text "Chart 1 Here" ]
          , div [ id "chart_1" ] [ text "Chart 1 Here" ]
          , div [ id "chart_2" ] [ text "Chart 1 Here" ]
        ]
      ]
  ]

update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
  case msg of
    From from ->
      ( { model | from = from }, Cmd.none )
    To to ->
      ( { model | to = to }, Cmd.none )
    Submit ->
      (model, routes model)
    Routes (Ok body) ->
      (model, highcharts (List.indexedMap toChart body))
    Routes (Err e) ->
      (model, Cmd.none)

toChart : Int -> ElevationProfile -> Chart
toChart idx ep =
  ({
    id = "chart_" ++ toString idx,
    payload = chartPayload ep
  })

chartPayload : ElevationProfile -> String
chartPayload ep =
  Debug.log (toString ep.points)
  -- Debug.log (toString encodePoints ep.points)
  """{"series":[{"data":""" ++ (Encode.encode 0 (encodePoints ep.points)) ++ """}]}"""

encodePoint : Point -> Encode.Value
encodePoint point =
  Encode.list [Encode.float point.x, Encode.float point.y]

encodePoints : List Point -> Encode.Value
encodePoints points =
  Encode.list (List.map encodePoint points)

  -- toString (List.map (\p -> "[" ++ (toString p.x) ++ "," ++ (toString p.y) ++ "]") points)

port highcharts : (List Chart) -> Cmd msg

routes : Model -> Cmd Msg
routes model =
  let
    url =
      "https://lazy-cyclist.herokuapp.com/route/" ++ model.from ++ "/" ++ model.to
    request =
      Http.get url elevationProfileListDecoder
  in
    Http.send Routes request

decodeRoutes : Decode.Decoder String
decodeRoutes =
  Decode.string

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
  Decode.map2 Point (Decode.field "x" Decode.float) (Decode.field "y" Decode.float)

subscriptions : Model -> Sub Msg
subscriptions model =
  Sub.none

main : Program Never Model Msg
main =
  program
    {
      init = init,
      view = view,
      update = update,
      subscriptions = subscriptions
    }
