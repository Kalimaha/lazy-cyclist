import { connect } from 'react-redux'
import Messages from '../components/messages'

const messageClassName = (valid) => valid ? 'alert alert-success' : 'alert alert-danger'

const mapStateToProps = (state) => {
  return {
    message: state.message,
    className: messageClassName(state.valid)
  }
}

const mapDispatchToProps = (dispatch) => {
  return {}
}

const MessagesContainer = connect(
  mapStateToProps,
  mapDispatchToProps
)(Messages)

export default MessagesContainer
