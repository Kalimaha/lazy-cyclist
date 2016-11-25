module Main exposing (..)

import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (onInput, onClick)
import Http
import Json.Decode as Decode

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
      Debug.log "OK"
      Debug.log (toString body)
      (model, Cmd.none)
    Routes (Err e) ->
      Debug.log "ERROR"
      Debug.log (toString e)
      (model, Cmd.none)

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
