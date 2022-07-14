import {React} from 'react';
import {Link} from 'react-router-dom';
import NavbarUser from '../components/NavbarUser';
function Userpage() {
   
    if(JSON.parse(localStorage.getItem("user")) && JSON.parse(localStorage.getItem("role"))[0].authority==="user")
    return (
        <div className="App2">
            <NavbarUser />
            <h2> Bun venit! </h2>
        </div>
      );
    else
    return(
          <div className='App2'><h1>Nu există autorizare de acces aici!</h1>
               <h2>Vă rugăm să vă autentificați și reveniti.Accesați butonul de mai jos.</h2>
            <div className='content'  style={{ display: "flex" , justifyContent: "space-around"}}>
                 <Link  to="/login"  style={{color: 'red' ,backgroundColor:'white',fontSize:'100',width:'55'}}>Log In </Link>
          </div>
          </div>
           
      )
}

export default Userpage;