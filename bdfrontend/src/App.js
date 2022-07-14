import React from "react";
import './App.css';
import {Component} from "react";
import Home from "./Home";
import Login from "./pages/Login";
import Register from "./pages/Register.js";
import Stock from "./pages/Stock.js";
import UserPage from "./pages/Userpage.js";
import Myapp from "./pages/Myapp.js";
import Addprog from "./pages/Addprog.js";
import Doctorpage from "./pages/Doctorpage.js";
import Requests from "./pages/Requests";
import {
  BrowserRouter as Router,
  Routes,
  Route
} from "react-router-dom";
import Confirmedprog from "./pages/Confirmedprog";
import Unconfirmed from "./pages/Unconfirmed";
import AdminPage from "./pages/Adminpage";
import Update from "./pages/Update";
import About from "./pages/About";
class App extends Component {
  constructor(props) {
    super(props);

  }
  render() {
      
    return ( 
      <div style={styles.app}>
      <Router>
        
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/stock" element={<Stock />} />
          <Route path="/userpage" element={<UserPage />} />
          <Route path="/myapp" element={<Myapp />} />
          <Route path="/addprog" element={<Addprog />} />
          <Route path="/doctorpage" element={<Doctorpage />} />
          <Route path="/confirmed" element={<Confirmedprog />} />
          <Route path="/unconfirmed" element={<Unconfirmed />} />
          <Route path="/apeluri" element={<Requests />} />
          <Route path="/adminpage" element={<AdminPage />} />
          <Route path="/update" element={<Update />} />
          <Route path="/about" element={<About />} />
        </Routes>
      </Router>
      <footer style={{textAlign:'center',background:"pink"}}>
    <div>
    <span>Powered by</span>
    <a href="https://ro.linkedin.com/in/anamaria-raita-a4353b212"> &copy; Anamaria Raita 2022</a>
    </div>
    </footer>
    </div>
    );
  }
}

export default App;

const styles = {
  app: {
    padding: 0,
  },
};