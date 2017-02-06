import React from 'react'
import ReactDOM from 'react-dom'
import { createStore } from 'redux'
import App from './js/components/app'
// import counter from './src/main/js/reducers/index'


// const store = createStore(counter)
const placeholder = document.getElementById('placeholder')

function render() {
  ReactDOM.render(
    <App />,
    placeholder
  )
}

render()
// store.subscribe(render)
