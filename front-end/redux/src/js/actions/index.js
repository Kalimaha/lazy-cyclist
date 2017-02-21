export const VALIDATE_FORM = (from, to) => {
  return {
    type: 'VALIDATE_FORM',
    from: from,
    to: to
  }
}

export const UPDATE_FROM = (from) => {
  return {
    type: 'UPDATE_FROM',
    from: from
  }
}

export const UPDATE_TO = (to) => {
  return {
    type: 'UPDATE_TO',
    to: to
  }
}
