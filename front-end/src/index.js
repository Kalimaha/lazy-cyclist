import React from 'react'
import ReactDOM from 'react-dom'
import { createStore } from 'redux'
import Form from './js/components/form'
// import counter from './src/main/js/reducers/index'


// const store = createStore(counter)
const placeholder = document.getElementById('placeholder')

function render() {
  ReactDOM.render(
    <Form />,
    placeholder
  )
}

render()
// store.subscribe(render)
