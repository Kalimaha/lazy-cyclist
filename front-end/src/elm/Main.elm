module Main exposing (..)

import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (onInput, onClick)

type alias Model = {
  from: String,
  to: String
}

init : ( Model, Cmd Msg )
init =
  ( { from = "", to = "" }, Cmd.none )

type Msg
  = From String
  | To String
  | Submit

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
      let
        url =
          create_url model
      in
        ( model, Cmd.none )

create_url : Model -> String
create_url model =
  Debug.log model.from
  "http://www.google.com/"

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
