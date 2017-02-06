import React, { PropTypes } from 'react'


const Form = ({ submitAction }) => (
  <div>
    <div className="row">
      <div className="col-lg-12">
        <label>
          From
        </label>
      </div>
      <div className="col-lg-12">
        <input className="form-control" placeholder="e.g. Federation Square"/>
      </div>
    </div>
    <br />
    <div className="row">
      <div className="col-lg-12">
        <label>
          To
        </label>
      </div>
      <div className="col-lg-12">
        <input className="form-control" placeholder="e.g. REA Group"/>
      </div>
    </div>
    <br />
    <div className="row">
      <div className="col-lg-12">
        <button onClick={submitAction} className="btn btn-primary" style={{width: "100%"}}>
          Route!
        </button>
      </div>
    </div>
  </div>
)

Form.propTypes = {
  submitAction: PropTypes.func.isRequired
}

export default Form
