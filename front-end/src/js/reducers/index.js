const initial_state = {
  from:     'Federation Square, Melbourne, Australia',
  to:       '511 Church St, Melbourne, Australia',
  valid:    true,
  message:  'Valid selection.'
}

const is_valid  = (from, to)  => (from.length > 0 && to.length > 0)
const message   = (valid)     => valid  ? 'Valid selection.'
                                        : 'Both "From" and "To" fields are required.'

const Reducer = (state = initial_state, action) => {
  switch (action.type) {
    case 'VALIDATE_FORM':
      if (state.valid)  { alert('valid request') }
      else              { alert('nope') }

      return initial_state
    case 'UPDATE_FROM':
      return Object.assign({}, state, {
        from:     action.from,
        valid:    is_valid(action.from, state.to),
        message:  message(is_valid(action.from, state.to))
      })
    case 'UPDATE_TO':
      return Object.assign({}, state, {
        to:       action.to,
        valid:    is_valid(state.from, action.to),
        message:  message(is_valid(state.from, action.to))
      })
    default:
      return state
  }
}

export default Reducer
