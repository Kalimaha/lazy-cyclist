import * as actions from '../../src/js/actions/index'

describe('Actions', () => {
  describe('VALIDATE_FORM', () => {
    it('creates an action to validate the form', () => {
      const expected_action = {
        type: 'VALIDATE_FORM',
        from: 'a',
        to: 'b'
      }
      expect(actions.VALIDATE_FORM('a', 'b')).toEqual(expected_action)
    })
  })

  describe('UPDATE_FROM', () => {
    it('creates an action to update the "from" field', () => {
      const expected_action = {
        type: 'UPDATE_FROM',
        from: 'a'
      }
      expect(actions.UPDATE_FROM('a')).toEqual(expected_action)
    })
  })

  describe('UPDATE_TO', () => {
    it('creates an action to update the "to" field', () => {
      const expected_action = {
        type: 'UPDATE_TO',
        to: 'b'
      }
      expect(actions.UPDATE_TO('b')).toEqual(expected_action)
    })
  })
})
