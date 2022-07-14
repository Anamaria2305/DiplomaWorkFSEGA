import React, { Component, useEffect, useState } from 'react';
import { useParams, useNavigate, Link} from 'react-router-dom';
import axios from 'axios'
import NavbarDoctor from '../components/NavbarDoctor';
import {useLocation} from 'react-router-dom';
import {Card,Button,Form,Table} from 'react-bootstrap'
import Popup from '../components/Popup';
function Doctorpage() {

    const [isOpen, setIsOpen] = useState(false);

    const togglePopup = () => {
        setIsOpen(!isOpen);
      }
    const [errorMessage, setMessage] = useState("");
    async function handleSubmit(event) {
        event.preventDefault()
        var {oldp,newp} = document.forms[0];

        var obj = JSON.parse(localStorage.getItem('user'));
        let headers = {
                "Content-type": "application/json; charset=UTF-8",
                "Authorization": 'Bearer '+ obj
         };
         const params = new URLSearchParams({
            oldpass: oldp.value
          }).toString();
          const params2 = new URLSearchParams({
            newpass: newp.value
          }).toString();
         try {
            var response= await axios.get("http://localhost:8080/staff/edit?"+params+"&"+params2,
                {headers: headers}
                )
            togglePopup()
          } catch (error) {
            
            setMessage("Parola veche nu este corectă!");
            
          }

    }
    if(JSON.parse(localStorage.getItem("user")) &&  JSON.parse(localStorage.getItem("role"))[0].authority==="doctor")
    return (
     
        <div className="App2">
               <NavbarDoctor></NavbarDoctor>
            <h2> WBun venit! </h2>
            <Button style={{float:"right",marginRight:"20px",marginTop:"-40px"}} variant="info"  onClick={()=>togglePopup()} >Schimbă Parola</Button>
        <div>
        {isOpen && <Popup
      content={<>

        <Form style={{fontSize:15}} onSubmit= {handleSubmit}>
          
                <Form.Group>
                    <Form.Label>Parola veche</Form.Label>
                    <Form.Control type="password"   required name= "oldp" placeholder="Parola veche"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Parola nouă</Form.Label>
                    <Form.Control type="password"   required name= "newp" placeholder="Parola nouă" />
                </Form.Group>
                <Form.Group>
                    <Form.Label style={{fontWeight: "bold",fontSize:15}}>{errorMessage}</Form.Label>
                </Form.Group>
                <Button class="but" variant="danger" type="submit" style={{margin:"8px"}}>
                    Confirmă
                </Button>
            </Form>

      </>}
      handleClose={togglePopup}
    />}
        </div>
        
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

export default Doctorpage;