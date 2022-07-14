import React, { useState } from "react";
// import "./Login.css";
import { useNavigate } from 'react-router-dom';
import{useEffect} from 'react';
import axios from 'axios'
import { Button ,Form} from "react-bootstrap";
import Navbar from "../components/Navbar.js";
import { CNP } from 'romanian-personal-identity-code-validator';
import { validate } from 'react-email-validator';
export default function Register() {
    let navigate = useNavigate();
    const [errorMessage, setMessage] = useState("");
    const [errorMessage2, setMessage2] = useState("");
    const [errorMessage3, setMessage3] = useState("");
    const [disbl, setDis] = useState(false);
    const [data2, setData2] = useState([]);
    useEffect(()=>{
        axios.get("http://localhost:8080/bloodtype/all")
            .then(res =>{
                setData2(res.data);
                
            })
            .catch(err =>{
                console.log(err);
            })  
    },[])

    async function handleSubmit(event) {

        event.preventDefault();

        var { name,uname, pass,cnp,name,tel,gr} = document.forms[0];
        
        try {
            var response= await axios.post("http://localhost:8080/users/save",{name:name.value, username: uname.value, password: pass.value,cnp:cnp.value,phone:tel.value,
                bloodtype:{id:gr.value}})

            navigate("/login")
          } catch (error) {
            
            setMessage("Acest e-mail este deja folosit");
            
          }
    }
    const onChange2 = (e) => {
        var email = e.target.value
        if(!validate(email)){
         setMessage("E-mail invalid.")
         setDis(true)
        }
        else
        {setDis(false)
        setMessage("")}
      }
      const onChange3 = (e) => {
        let cnp = new CNP(e.target.value); 
        if(!cnp.isValid()){
         setMessage2("CNP invalid.")
         setDis(true)
        }
        else{
        setMessage2("")
        setDis(false)
        }
      }
      const onChange4 = (e) => {
        var nr = e.target.value
        let nrREG = new RegExp('^(0([7][457623])([0-9]){7})$')
        if(!nrREG.test(nr)){
         setMessage3("Nr de telefon invalid.")
         setDis(true)
        }
        else
        {
        setMessage3("")
        setDis(false)
        }
      }
      
    return (
        <div className="App2"  >    <Navbar></Navbar>
            <div style={{
            position:'absolute',
            display: 'block',
            width: 700,
            left:450
        }}>
            <h4>Înregistrare</h4>
            <Form style={{fontSize:13}} onSubmit= {handleSubmit}>
            <Form.Group>
                    <Form.Label>Introduceți numele complet</Form.Label>
                    <Form.Control type="text" required name= "name"
                        placeholder="Introduceți numele complet" />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Introduceți adresa de e-mail</Form.Label>
                    <Form.Control type="email" required name= "uname"  onChange={(e) => onChange2(e)}
                        placeholder="Introduceți adresa de e-mail" />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Introduceți parola</Form.Label>
                    <Form.Control type="password" required name= "pass" placeholder="Introduceți parola" />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Introduceți CNP</Form.Label>
                    <Form.Control type="number" required name= "cnp" onChange={(e) => onChange3(e)} placeholder="Introduceți CNP" />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Introduceți numarul de telefon</Form.Label>
                    <Form.Control type="number" required name= "tel" onChange={(e) => onChange4(e)} placeholder="Introduceți numarul de telefon" />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Introduceți Grupa de Sânge</Form.Label>
                    <Form.Select aria-label="Default select example" name= "gr">
                    { data2.map((bloodtype) => (
                                   <option value={[bloodtype.id]}>{[bloodtype.blood," ",bloodtype.rh]}</option>
                               ))

                               }
                    </Form.Select>
                </Form.Group>
                <Form.Group>
                    <Form.Label style={{fontWeight: "bold",fontSize:15}}>{errorMessage}</Form.Label>
                    <Form.Label style={{fontWeight: "bold",fontSize:15}}>{errorMessage2}</Form.Label>
                    <Form.Label style={{fontWeight: "bold",fontSize:15}}>{errorMessage3}</Form.Label>
                </Form.Group>
                <Button disabled={disbl} class="but" variant="danger" type="submit" style={{margin:"8px"}}>
                     Creează
                </Button>
            </Form>
            </div>
        </div>
    );
}