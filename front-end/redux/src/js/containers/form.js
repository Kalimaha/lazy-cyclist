import { connect } from 'react-redux'
import Form from '../components/form'
import { VALIDATE_FORM, UPDATE_FROM, UPDATE_TO } from '../actions/index'


const mapStateToProps = (state) => {
  return {
    from:   state.from,
    to:     state.to,
    valid:  state.valid
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    submitAction: ()  => dispatch(VALIDATE_FORM('QWE', 'RTY')),
    updateFrom:   (e) => dispatch(UPDATE_FROM(e.target.value)),
    updateTo:     (e) => dispatch(UPDATE_TO(e.target.value))
  }
}

const FormContainer = connect(
  mapStateToProps,
  mapDispatchToProps
)(Form)

export default FormContainer
