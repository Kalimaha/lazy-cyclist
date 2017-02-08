import React from 'react'
import { render } from 'react-dom'
import { createStore } from 'redux'
import { Provider } from 'react-redux'
import App from './js/components/app'
import Reducer from './js/reducers/index'


const store = createStore(Reducer)
const placeholder = document.getElementById('placeholder')

render(
  <Provider store={store}>
    <App />
  </Provider>,
  placeholder
)
