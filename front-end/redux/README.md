# Overview

This folder contains the Elm source code for the Lazy Cyclist app. Elm is in charge of:

* Displaying a simple form
* Sending the form data to the [Lazy Cyclist REST service](https://lazy-cyclist.herokuapp.com/)
* Parsing the JSON response
* Creating the Highcharts payload for the charts
* Sending the payload to Highcharts through a port

## Requirements

Elm installed is required to produce the final JavaScript code. Please refer to the [Elm's installation guide](https://guide.elm-lang.org/install.html).

## Compile

The `bin` folder in the project's root contains the `compile-front-end` script that installs the required dependencies and produces the final `front-end/dist/main.js` JavaScript code. To run the script, execute the following command from the root of the project:

```
./bin/compile-front-end
```

## Host Page

Once the Elm code has been traslated into JavaScript, it is possible to preview it by opening the `front-end/index.html` page in the browser. At the moment, only Chrome has been tested.

# My 2 cents about Elm

This is my first project with Elm, and I use it as a playground to test the technology, and probably most of the issues I have experienced 
are due to a poor knowledge of the language. Overall Elm is a very interesting language, but if I had to start a new project tomorrow, 
I will probably pick Redux (_which has basically the same architecture_) over Elm.

## The Good

* **Types:** it makes easier to write and maintain your code
* **Great error messages:** it makes easier to debug your code, even when you are just a beginner

## The Bad

* **All in one:** with Elm the model, the view, and the controller are all mixed in one language. This makes the collaboration between 
developers and graphic designer more difficult. For example, with a templating system, developers can implement of the business 
logic and designer can take care of the look-and-feel. In Elm the page structure is defined by the model, which makes the work of 
designers more difficult.

## The Ugly

* **Compiler:** I find annoying that you have to compile (_instead of refreshing the page_) everytime there is a change in the 
front-end. Elm also comes with `elm-reactor` that updates the page at every change, but it seems to me that it does not work 
with the `embed` mode.
* **Interoperability with JavaScript:** To interact with JavaScript libraries (_Highcharts in my case_) you are required to define 
a `port` in Elm, but also to write JavaScript code, which forces you to know (_and synch, and maintain_) two different source codes.
* **JSON parsing:** I was forced to model the JSON output of an API into Elm types to consume the service, which account for nearly 
half of my source code. Maybe there's a more generic way to _explore_ a JSON, not sure yet.
