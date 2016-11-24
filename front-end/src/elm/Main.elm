module Main exposing (..)

import Html exposing (Html, div, text, program, h1, form, label, input, br)
import Html.Attributes exposing (..)

type alias Model =
  String

init : ( Model, Cmd Msg )
init =
  ( "Hello", Cmd.none )

type Msg =
  NoOp

view : Model -> Html Msg
view model =
  div [ class "row" ] [
    div [ class "col-lg-12" ] [
      Html.form [] [
        div [ class "form-group" ] [
          label [ for "from" ] [ text "From" ]
          , input [
              class "form-control"
              , placeholder "e.g. Federation Square, Melbourne, Australia"
            ] []
          , br [] []
          , label [ for "to" ] [ text "To" ]
          , input [
              class "form-control"
              , placeholder "e.g. 511 Church St, Melbourne, Australia"
            ] []
        ]
      ]
    ]
  ]

update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
  case msg of
    NoOp ->
      ( model, Cmd.none )

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
