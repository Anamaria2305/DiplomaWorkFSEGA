import React, {useState,useEffect } from 'react';
import { Link,useNavigate} from 'react-router-dom';
import axios from 'axios'
import NavbarDoctor from '../components/NavbarDoctor';
import NavbarUser from '../components/NavbarUser';
import Navbar from '../components/Navbar';
import {Card,Button,Form} from 'react-bootstrap'
function Requests() {

    let navigate = useNavigate();
    const [unconfirmedReqDoctor, setunconfirmedReqDoctor] = useState(["Nu exista cereri"]);
    const [confirmedReq, setconfirmedReq] = useState(["Nu exista cereri"]);
    const [unconfirmedReqUser, setunconfirmedReqUser] = useState(["Nu exista cereri"]);
    const [show, setShow] = useState(false);
    const [show2, setShow2] = useState(false);
    const [show3, setShow3] = useState(false);
    const [errorMessage, setMessage] = useState("");
    const [bt, setBt] = useState([]);
 
   useEffect(() => {


        var obj = JSON.parse(localStorage.getItem('user'));
          
        
        let headers2 = {
            "Content-type": "application/json; charset=UTF-8",
            "Authorization": 'Bearer '+ obj
             };

        axios.get("http://localhost:8080/requests/unconfirmed",{ headers: headers2})
            .then(res => {
                setunconfirmedReqDoctor(res.data);
       
            })
            .catch(err => {
                console.log(err);
            })
        axios.get("http://localhost:8080/requests/confirmed",{ headers: headers2})
            .then(res => {
                setconfirmedReq(res.data);
              
            })
            .catch(err => {
                console.log(err);
            })
            axios.get("http://localhost:8080/requests/unconfirmedU",{ headers: headers2})
            .then(res => {
                setunconfirmedReqUser(res.data);
               
            })
            .catch(err => {
                console.log(err);
            })


            axios.get("http://localhost:8080/bloodtype/all")
            .then(res =>{
                setBt(res.data);
                
            })
            .catch(err =>{
                console.log(err);
            })  
    }, [])

    function fromint(nr){
        if(nr == 1){
            return "Completă"
        }
        else
            return "În derulare"
    }

    function toDate(date){
        return date?.split('T')[0]
    }
    function toTime(date){
        return date?.split('T')[1].substring(0,5)
    }

   const handleClick = (nr) => {
       
        var obj = JSON.parse(localStorage.getItem('user'));

             const params = new URLSearchParams({
                id: nr,
            }).toString();

            const url="http://localhost:8080/requests/chconfirmed?"+params

            let headers2 = {
                "Content-type": "application/json; charset=UTF-8",
                "Authorization": 'Bearer '+ obj
                 };
    
            axios.get(url,{ headers: headers2})
                .then(res => {
                    navigate('/doctorpage')
                })
                .catch(err => {
                    console.log(err);
                })

            

      }
    
    const  handleClickToogle = () =>{
        setShow(true)
        setShow2(false)
        setShow3(false)
    }
    const  handleClickTooglei = () =>{
        setShow(false)
        setShow2(true)
        setShow3(false)
    }
    const  handleClickToogle3 = () =>{
        setShow(false)
        setShow2(false)
        setShow3(true)
    }


    function seeNav(obj){
        if(JSON.parse(localStorage.getItem("user")))
        obj=JSON.parse(localStorage.getItem("role"))[0].authority
        else
        obj="logout"
        
        if(obj==="doctor")
        return <NavbarDoctor/>
        else if(obj==="user")
        return <NavbarUser/>
        else
        return <Navbar/>
       
    }
    function returnButton(data,requests){
        if(data==="doctor")
        return  <Button variant="info"  onClick={() => handleClick(requests.id)}  >Confirma</Button>
    }
    function returnButton2(data){
        if(data==="doctor")
        return  <Button style={{margin:"10px"}} variant="danger"  onClick={() => handleClickToogle3()}  >Adaugă un nou apel la donare</Button>
    }
    function display(auth){
       if(auth==="user") 
        return ( <div style={{ display: show ? "inline-flex" : "none"}}  class="row row-cols-3 g-1">
        {unconfirmedReqUser.map((requests) => (
        <Card bg='danger' style={{ width: '18.5rem',margin:'8px' }}>
        <Card.Header style={{fontWeight: "bold"}}>Pacient:{requests?.targetName}  <img src="req.png" height='30px' style={{float:'right'}}></img></Card.Header>
        <Card.Img variant="top"  />
        <Card.Body>
        <Card.Title>Grupa de sânge: {requests?.bloodtype?.blood}, {requests?.bloodtype?.rh}  <br></br>Spitalul: {requests?.staff?.hospital?.name}</Card.Title>
            <Card.Text>
            Data și ora cererii:<br></br>
             {toDate(requests?.requestDate)},
             {toTime(requests?.requestDate)}<br></br>
            Condiție medicala: {requests?.illness}<br></br>
            Cererea este: {fromint(requests?.confirmed)}
            </Card.Text>
            <Button onClick={() => navigate('/addprog')}>Adaugă programare</Button>
        </Card.Body>
        </Card>
      
           ))}
         </div>)
         else
         return (
         <div style={{ display: show ? "inline-flex" : "none"}}  class="row row-cols-3 g-1">
        {unconfirmedReqDoctor.map((requests) => (
        <Card bg='danger' style={{ width: '18.5rem',margin:'8px' }}>
        <Card.Header style={{fontWeight: "bold"}}>Pacient:{requests?.targetName}  <img src="req.png" height='30px' style={{float:'right'}}></img></Card.Header>
        <Card.Img variant="top"  />
        <Card.Body>
        <Card.Title>Grupa de sânge: {requests?.bloodtype?.blood}, {requests?.bloodtype?.rh}  <br></br>Spitalul: {requests?.staff?.hospital?.name}</Card.Title>
            <Card.Text>
            Data și ora cererii:<br></br>
             {toDate(requests?.requestDate)},
             {toTime(requests?.requestDate)}<br></br>
            Condiție medicala: {requests?.illness}<br></br>
            Cererea este: {fromint(requests?.confirmed)}
            </Card.Text>
            {returnButton(JSON.parse(localStorage.getItem("role"))[0].authority,requests)}
        </Card.Body>
        </Card>
      
           ))}
         </div>
        )
    }
    async function handleSubmit(event) {


        var { uname,ill, gr} = document.forms[0];
        var obj = JSON.parse(localStorage.getItem('user'));
        let headers2 = {
            "Content-type": "application/json; charset=UTF-8",
            "Authorization": 'Bearer '+ obj
             };

             axios.post("http://localhost:8080/requests/save",{targetName:uname.value,illness:ill.value,confirmed:0, bloodtype:{id:gr.value}},{ headers: headers2})
             .then(response => {
                setMessage("Cererea a fost adăugată")
     
             })
             .catch(({ response }) => {
                 if (typeof response !== "undefined")
                     setMessage("Ceva a mers prost.Mai încercati!");
     
             })
     
    }
    if(JSON.parse(localStorage.getItem("user")) && (JSON.parse(localStorage.getItem("role"))[0].authority==="doctor" || JSON.parse(localStorage.getItem("role"))[0].authority==="user"))
    return (
        <div className="App3">
            {seeNav("logout")}
            <h2 style={{margin:"10px"}}>Zona apelurilor la donare </h2>
            <div>
           <Button style={{margin:"10px"}}  variant="danger"   onClick={() => handleClickToogle()}  >Vezi apelurile active</Button>
           <Button style={{margin:"10px"}} variant="danger"   onClick={() => handleClickTooglei()} >Vezi apelurile inactive</Button>
           {returnButton2(JSON.parse(localStorage.getItem("role"))[0].authority)}
           </div>
        
         {display(JSON.parse(localStorage.getItem("role"))[0].authority)}         

        <div style={{ display: show2 ? "inline-flex" : "none"}}  class="row row-cols-3 g-1">
        {confirmedReq.map((requests) => (
        <Card bg='danger' style={{ width: '18.5rem',margin:'8px' }}>
        <Card.Header style={{fontWeight: "bold"}}>Pacient:{requests?.targetName}  <img src="req.png" height='30px' style={{float:'right'}}></img></Card.Header>
        <Card.Img variant="top"  />
        <Card.Body>
        <Card.Title>Grupa de sânge: {requests?.bloodtype?.blood}, {requests?.bloodtype?.rh}  <br></br>Spitalul: {requests?.staff?.hospital?.name}</Card.Title>
            <Card.Text>
            Data și ora cererii:<br></br>
             {toDate(requests?.requestDate)},
             {toTime(requests?.requestDate)}<br></br>
            Condiție medicala: {requests?.illness}<br></br>
            Cererea este: {fromint(requests?.confirmed)}
            </Card.Text>
        </Card.Body>
        </Card>
      
           ))}
         </div>   
         <div style={{ display: show3 ? "block" : "none",margin:"15px"}}  class="row row-cols-3 g-1">
         <Form   onSubmit= {handleSubmit}>
                <Form.Group>
                    <Form.Label>Nume pacient:</Form.Label>
                    <Form.Control type="text" required name= "uname"
                        placeholder="Numele pacientului" />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Conditia medicala:</Form.Label>
                    <Form.Control type="text" required name= "ill" placeholder="Conditia medicala" />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Grupa de Sânge:</Form.Label>
                    <Form.Select aria-label="Default select example" name= "gr">
                    { bt.map((bloodtype) => (
                                   <option value={[bloodtype.id]}>{[bloodtype.blood," ",bloodtype.rh]}</option>
                               ))

                               }
                    </Form.Select>
                </Form.Group>
                <Form.Group>
                    <Form.Label style={{fontWeight: "bold",fontSize:20}}>{errorMessage}</Form.Label>
                </Form.Group>
                <Button class="but" variant="danger" type="submit"  style={{margin:"20px"}}>
                   Salvează
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

export default Requests;