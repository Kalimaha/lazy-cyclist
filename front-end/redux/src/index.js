import React from 'react'
import { render } from 'react-dom'
import { createStore, applyMiddleware, compose } from 'redux'
import { Provider } from 'react-redux'
import App from './js/components/app'
import Reducer from './js/reducers/index'
import thunkMiddleware from 'redux-thunk'


const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose
const store = createStore(
  Reducer,
  composeEnhancers(applyMiddleware(thunkMiddleware))
)
const placeholder = document.getElementById('placeholder')

render(
  <Provider store={store}>
    <App />
  </Provider>,
  placeholder
)
