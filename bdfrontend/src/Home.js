import React from "react";
import { useNavigate, Link, useHistory } from "react-router-dom";
import './App.css';
import { Component } from "react";
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from './components/Navbar';
import background from "./blood-donation2.gif";
class Home extends Component {

  constructor(props) {
    super(props);
    this.state = {
      data: [],
    }

  }

  render() {
    return (
      <div >
      <div ><Navbar></Navbar>
        <div className="App" >
          <div className='content' >
          </div>

        </div>
      </div>
      </div>
    );
  }
}

export default Home;