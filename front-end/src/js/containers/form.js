import { connect } from 'react-redux'
import Form from '../components/form'

const mapStateToProps = (state) => {
  return {}
}

const mapDispatchToProps = (dispatch) => {
  return {
    submitAction: () => {
      console.log('asd')
    }
  }
}

const FormContainer = connect(
  mapStateToProps,
  mapDispatchToProps
)(Form)

export default FormContainer
