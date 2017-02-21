import React, { PropTypes } from 'react'


const Messages = ({ message, className }) => (
  <div className={className}>
    {message}
  </div>
)

Messages.propTypes = {
  message: PropTypes.string.isRequired
}

export default Messages
