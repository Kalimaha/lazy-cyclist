import React, { Component } from 'react'

class Form extends Component {
  constructor(props) {
    super(props)
  }

  render() {
    return (
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
            <button className="btn btn-primary" style={{width: "100%"}}>
              Route!
            </button>
          </div>
        </div>
      </div>
    )
  }
}

export default Form
