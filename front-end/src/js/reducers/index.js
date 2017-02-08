const initial_state = {
  from: 'Federation Square, Melbourne, Australia',
  to: '511 Church St, Melbourne, Australia',
  valid: true,
  message: 'Valid selection.'
}

const is_valid = (from, to) => (from.length > 0 && to.length > 0)

const message = (valid) => valid ? 'Valid selection.' : 'Both "From" and "To" fields are required.'

const Reducer = (state = initial_state, action) => {
  switch (action.type) {
    case 'VALIDATE_FORM':
      if (state.valid) {
        alert('valid request')
      } else {
        alert('nope')
      }
      return initial_state
    case 'UPDATE_FROM':
      state.from = action.from
      state.valid = is_valid(state.from, state.to)
      state.message = message(state.valid)
      console.log(state)
      return state
    case 'UPDATE_TO':
      state.to = action.to
      state.valid = is_valid(state.from, state.to)
      state.message = message(state.valid)
      return state
    default:
      return state
  }
}

export default Reducer
