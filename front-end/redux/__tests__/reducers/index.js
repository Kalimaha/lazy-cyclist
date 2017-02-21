import Reducer from '../../src/js/reducers/index'

describe('Reducer', () => {
  const from          = 'Federation Square, Melbourne, Australia'
  const to            = '511 Church St, Melbourne, Australia'
  const initial_state = {
    from:     from,
    to:       to,
    valid:    true,
    message:  'Valid selection.'
  }

  it('returns the initial state', () => {
    expect(Reducer(undefined, {})).toEqual(initial_state)
  })

  it('handles the UPDATE_FROM action', () => {
    expect(Reducer(initial_state, { type: 'UPDATE_FROM', from: 'a' })).toEqual({
      from:     'a',
      to:       to,
      valid:    true,
      message:  'Valid selection.'
    })
  })

  it('handles the UPDATE_TO action', () => {
    expect(Reducer(initial_state, { type: 'UPDATE_TO', to: 'b' })).toEqual({
      from:     from,
      to:       'b',
      valid:    true,
      message:  'Valid selection.'
    })
  })

  it('provides a message when the selection is invalid', () => {
    expect(Reducer(initial_state, { type: 'UPDATE_FROM', from: '' })).toEqual({
      from:     '',
      to:       to,
      valid:    false,
      message:  'Both "From" and "To" fields are required.'
    })

    expect(Reducer(initial_state, { type: 'UPDATE_TO', to: '' })).toEqual({
      from:     from,
      to:       '',
      valid:    false,
      message:  'Both "From" and "To" fields are required.'
    })
  })
})
