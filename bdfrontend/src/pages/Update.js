import React, { useState } from "react";
import {Link} from 'react-router-dom';
import axios from 'axios'
import { Button ,Form} from "react-bootstrap";
import NavbarDoctor from "../components/NavbarDoctor.js";
import "../components/button.css";
import{useEffect} from 'react';
export default function Update() {
    const [errorMessage, setMessage] = useState("");
    const [errorMessage2, setMessage2] = useState("");
    const [data2, setData2] = useState([]);
    const [data3, setData3] = useState([]);
    const [stoc, setStoc] = useState([]);
    const [disbl, setDis] = useState(false);
    useEffect(()=>{
        var obj = JSON.parse(localStorage.getItem('user'));
        let headers2 = {
            "Content-type": "application/json; charset=UTF-8",
            "Authorization": 'Bearer '+ obj
             };

        axios.get("http://localhost:8080/bloodtype/all")
            .then(res =>{
                setData2(res.data);
                 axios.get("http://localhost:8080/stock/hospitalid",{ headers: headers2})
                .then(res => {
                    setData3(res.data)
                    setStoc(res.data[0].quantity)
                })
                .catch(err => {
                    console.log(err);
                })

            })
            .catch(err =>{
                console.log(err);
            })  
    },[])
    async function handleSubmit(event) {

        var { gr,cuan} = document.forms[0];
        var obj = JSON.parse(localStorage.getItem('user'));
        let headers2 = {
            "Content-type": "application/json; charset=UTF-8",
            "Authorization": 'Bearer '+ obj
             };

             const params = new URLSearchParams({
                gr: gr.value
              }).toString();
              const params2 = new URLSearchParams({
                cuan: cuan.value
              }).toString();
             try {
                var response= await axios.get("http://localhost:8080/stock/edit?"+params+"&"+params2,
                    {headers: headers2}
                    )
              } catch (error) {
                
                setMessage("Canitatea introdusa este gresita!");
                
              }
       
    
    }
    const onChange2 = (e) => {
        var nr = e.target.value
        for(let x of data3){
            if(x?.bloodtype?.id == nr){
                
                setStoc(x?.quantity)
            }
        }
        
      }
      const onChange3 = (e) => {
    
        if(e.target.value<0){
        e.target.value=0
        }
        if(stoc - e.target.value <0){
            setMessage2("Nu puteti consuma mai mult sânge decât există!")
            setDis(true)
        }
        else
        {setMessage2("")
         setDis(false)
        }
      }
      function displayst(nr){
        if(typeof nr ==='number')
       { return nr.toFixed(2)}
        else 
        {return nr}
      }
    if(JSON.parse(localStorage.getItem("user")) && JSON.parse(localStorage.getItem("role"))[0].authority==="doctor")
    return (
        <div className="App3" >    <NavbarDoctor></NavbarDoctor>
            <div style={{
            position:'absolute',
            display: 'block',
            width: 700,
            padding: 100,
            left:300
        }}>
            <h4>Raport consum sânge </h4>
            <Form  onSubmit= {handleSubmit}>
                <Form.Group>
                    <Form.Label>Introdu Grupa</Form.Label>
                    <Form.Select aria-label="Default select example" name= "gr" onChange={(e) => onChange2(e)}>
                    { data2.map((bloodtype) => (
                                   <option value={[bloodtype.id]}>{[bloodtype.blood," ",bloodtype.rh]}</option>
                               ))

                               }
                    </Form.Select>
                </Form.Group>
                <Form.Group>
                    <Form.Label style={{fontWeight: "bold",fontSize:20}}>Stoc disponibil: {displayst(stoc)} litri</Form.Label>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Cantitatea de sânge consumat:</Form.Label>
                    <Form.Control type="number" step="0.01" required name= "cuan" placeholder="Introduceti cantitatea" 
                   onChange={(e) => onChange3(e)}/>
                </Form.Group>
                <Form.Group>
                    <Form.Label style={{fontWeight: "bold",fontSize:20}}>{errorMessage}{errorMessage2}</Form.Label>
                </Form.Group>
                <Button disabled={disbl} class="but" variant="danger" type="submit"  style={{margin:"20px"}}>
                    Actualizează
                </Button>
            </Form>
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