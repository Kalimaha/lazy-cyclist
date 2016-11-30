port module Main exposing (..)

import Models exposing (..)
import Constructors exposing (..)
import Decoders exposing (..)
import Encoders exposing (..)

import Http
import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (onInput, onClick)
import Json.Encode as Encode



init : ( Model, Cmd Msg )
init =
  (
    {
      to = "2 McGoun St, Melbourne, Australia",
      from   = "511 Church St, Melbourne, Australia"
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
          div [ id "chart_0" ] []
          , hr [] []
          , div [ id "chart_1" ] []
          , hr [] []
          , div [ id "chart_2" ] []
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
    payload = chartPayload idx ep
  })

chartPayload : Int -> ElevationProfile -> String
chartPayload idx ep =
  let hc =
    Constructors.highcharts (chartTitle idx) (chartSubTitle ep) "Elevation [m]" ep.points ep.climbs
  in
    Encode.encode 0 (encodeHighcharts hc)

chartTitle : Int -> String
chartTitle idx =
  "Route " ++ toString (1 + idx)

chartSubTitle : ElevationProfile -> String
chartSubTitle ep =
  String.concat [
    "Total Distance: ",
    toString ep.totalDistance,
    "[m], Total Climbs: ",
    toString (List.length ep.climbs)
  ]

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
