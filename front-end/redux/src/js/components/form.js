import React, { PropTypes } from 'react'


const Form = ({ from, to, updateFrom, updateTo, submitAction, valid }) => (
  <div>
    <div className="row">
      <div className="col-lg-12">
        <label>
          From
        </label>
      </div>
      <div className="col-lg-12">
        <input  className="form-control"
                placeholder="e.g. Federation Square, Melbourne, Australia"
                defaultValue={from}
                onChange={updateFrom} />
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
        <input  className="form-control"
                placeholder="e.g. 511 Church St, Melbourne, Australia"
                defaultValue={to}
                onChange={updateTo} />
      </div>
    </div>
    <br />
    <div className="row">
      <div className="col-lg-12">
        <label>
          Extra Elevation Points
        </label>
      </div>
      <div className="col-lg-12">
      <label className="radio-inline"><input type="radio" name="optradio" checked=""/>0</label>
      <label className="radio-inline"><input type="radio" name="optradio" checked="checked"/>1</label>
      <label className="radio-inline"><input type="radio" name="optradio" checked=""/>2</label>
      </div>
    </div>
    <br />
    <div className="row">
      <div className="col-lg-12">
        <button disabled={!valid} onClick={submitAction} className="btn btn-primary" style={{width: "100%"}}>
          Route!
        </button>
      </div>
    </div>
  </div>
)

Form.propTypes = {
  submitAction: PropTypes.func.isRequired,
  updateFrom: PropTypes.func.isRequired,
  updateTo: PropTypes.func.isRequired
}

export default Form
