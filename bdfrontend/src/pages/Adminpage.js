import React, { Component, useEffect, useState } from 'react';
import { useParams, useNavigate, Link} from 'react-router-dom';
import axios from 'axios'
import NavAdmin from '../components/NavAdmin';
import {useLocation} from 'react-router-dom';
import {Card,Button,Form,Table} from 'react-bootstrap'
import Popup from '../components/Popup';
function AdminPage() {

    const [isOpen, setIsOpen] = useState(false);
    const [spital, setSpital] = useState(["Nu aveți programări"]);
    const [isOpensp, setIsOpensp] = useState(false);
    const [isOpendr, setIsOpendr] = useState(false);
    const [errorMessage, setMessage] = useState("");
    const [errorMessage2, setMessage2] = useState("");
    const [errorMessage3, setMessage3] = useState("");
    const [errorMessage4, setMessage4] = useState("");
    const [disbl, setDis] = useState(false);
    const [disbl2, setDis2] = useState(false);
    useEffect(() => {

        var obj = JSON.parse(localStorage.getItem('user'));
          
        
        let headers2 = {
            "Content-type": "application/json; charset=UTF-8",
            "Authorization": 'Bearer '+ obj
             };
        axios.get("http://localhost:8080/hospital/admin",{headers:headers2})
            .then(res => {
                setSpital(res.data);
                
            })
            .catch(err => {
               
            })
    }, [])

    const togglePopup = () => {
        setIsOpen(!isOpen);
      }
    const togglePopup2 = () => {
        setIsOpensp(!isOpensp);
      }
   
    const togglePopup3 = () => {
        setIsOpendr(!isOpendr);
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
      const onChange5 = (e) => {
        var nr = e.target.value
        let nrREG = new RegExp('^(0([7][457623])([0-9]){7})$')
        if(!nrREG.test(nr)){
         setMessage4("Nr de telefon invalid.")
         setDis2(true)
        }
        else
        {
        setMessage4("")
        setDis2(false)
        }
      }
    async function handleSubmit(event) {
       
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
    async function editHp(event) {

        var obj = JSON.parse(localStorage.getItem('user'));
        var {sp,addr,tele} = document.forms[0];
        
        const id = new URLSearchParams({
            id: spital.id,
        }).toString();
        const name = new URLSearchParams({
            name: sp.value,
        }).toString();
        const add = new URLSearchParams({
            address: addr.value,
        }).toString();
        const tel = new URLSearchParams({
            nr: tele.value,
        }).toString();
        let headers2 = {
            "Content-type": "application/json; charset=UTF-8",
            "Authorization": 'Bearer '+ obj
             };
             try {
                var response= await axios.get("http://localhost:8080/hospital/edit?"+id+"&"+name+"&"+add+"&"+tel,{headers: headers2})
                togglePopup2()
              } catch (error) {
                
                setMessage2("Ceva nu a mers bine.Mai încercați.");
                
              }
    }
    async function addDr(event) {

        var obj = JSON.parse(localStorage.getItem('user'));
        let headers2 = {
            "Content-type": "application/json; charset=UTF-8",
            "Authorization": 'Bearer '+ obj
             };
    
         var { dr,drn, teldr} = document.forms[0];
        
             try {
                 var response= await axios.post("http://localhost:8080/staff/save",{ username: drn.value, password: drn.value+teldr.value,name:dr.value,phonenumber:teldr.value,role:"doctor"},
                 { headers: headers2})
     
               } catch (error) {
                 
                 setMessage3("Numele de utilizator sau numarul exista deja.");
                 
               }
    }
    if(JSON.parse(localStorage.getItem("user")) && JSON.parse(localStorage.getItem("role"))[0].authority==="admin")
    return (
     
        <div className="App2">
               <NavAdmin></NavAdmin>
            <h2> Bun venit! </h2>
            <Button style={{float:"right",marginRight:"20px",marginTop:"-40px",width:"150px"}} variant="info"  onClick={()=>togglePopup()} >Schimbă Parola</Button>
            <Button style={{float:"right",marginRight:"20px", marginTop:"10px",width:"150px",backgroundColor:"#5FE88D",color:"black" }}variant="success"  onClick={()=>togglePopup3()} >Adaugă Medici</Button>
            <Button style={{float:"right",marginRight:"-150px",marginTop:"60px",width:"150px",backgroundColor:"#78BE21",color:"black" }}variant="success"  onClick={()=>togglePopup2()} >Editeaza Spitalul</Button>
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
     {isOpensp && <Popup
      content={<>

        <Form style={{fontSize:15}} onSubmit= {editHp} >
          
                <Form.Group>
                    <Form.Label>Nume Spital</Form.Label>
                    <Form.Control type="text" defaultValue={spital.name}  required name= "sp"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Adresa</Form.Label>
                    <Form.Control type="text" defaultValue={spital.address}  required name= "addr"  />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Nr. de telefon</Form.Label>
                    <Form.Control type="text"  defaultValue={spital.phonenumber} onChange={(e) => onChange4(e)}  required name= "tele"  />
                </Form.Group>
                <Form.Group>
                    <Form.Label style={{fontWeight: "bold",fontSize:15}}>{errorMessage2}{errorMessage3}</Form.Label>
                </Form.Group>
                <Button disabled={disbl} class="but" variant="danger" type="submit" style={{margin:"8px"}}>
                    Salvează
                </Button>
            </Form>

      </>}
      handleClose={togglePopup2}
    />}
    {isOpendr && <Popup
      content={<>

        <Form style={{fontSize:15}} onSubmit= {addDr} >
          
                <Form.Group>
                    <Form.Label>Nume Doctor</Form.Label>
                    <Form.Control type="text"  required name= "dr"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Nume de utilizator</Form.Label>
                    <Form.Control type="text"  required name= "drn"  />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Nr. de telefon</Form.Label>
                    <Form.Control type="text" onChange={(e) => onChange5(e)}  required name= "teldr"  />
                </Form.Group>
                <Form.Group>
                    <Form.Label style={{fontWeight: "bold",fontSize:15}}>{errorMessage2}{errorMessage4}</Form.Label>
                </Form.Group>
                <Button disabled={disbl2} class="but" variant="danger" type="submit" style={{margin:"8px"}}>
                    Adaugă
                </Button>
            </Form>

      </>}
      handleClose={togglePopup3}
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

export default AdminPage;