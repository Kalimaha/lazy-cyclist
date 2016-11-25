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
  | Routes (Result Http.Error String)

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
  in
    Http.send Routes (Http.get url decodeRoutes)

decodeRoutes : Decode.Decoder String
decodeRoutes =
  Decode.string

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
