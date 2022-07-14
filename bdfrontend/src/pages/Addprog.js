import React, { useState ,useEffect} from "react";
// import "./Login.css";
import { useNavigate } from 'react-router-dom';
import axios from 'axios'
import { Button ,Form} from "react-bootstrap";
import NavbarUser from "../components/NavbarUser.js";
import { Link} from 'react-router-dom';

export default function Login() {
    let navigate = useNavigate();
    const [errorMessage, setMessage] = useState("");
    const [day, setDay] = useState("");
    const [data, setDate] = useState([]);
    const [data2, setData2] = useState([]);
    const [avh, setAvh] = useState([]);
    const [vizibil, setVisible] = useState(false);
    const [timp, setTimp]=useState(["7:30 AM","8:00 AM"])
    useEffect(()=>{
     
        axios.get("http://localhost:8080/hospital/all")
            .then(res =>{
                setData2(res.data);
                
            })
            .catch(err =>{
                console.log(err);
            })  
    },[])
    async function handleSubmit(event) {
        
        event.preventDefault()
        var { date22, hosp} = document.forms[0];
        const params = new URLSearchParams({
            date: date22.value
          }).toString();

          try {
            var response= await axios.get("http://localhost:8080/appointment/hours?"+params)
            setAvh(response.data)
            setVisible(true)
          } catch (error) {
            
            setMessage("Ceva nu a mers bine.");
            
          }
          
    }
    async function handleSubmit2(event) {
        
        event.preventDefault()
        var { date22, hosp} = document.forms[0];
        var { oraalesa} = document.forms[1];
        var obj = JSON.parse(localStorage.getItem('user'));
        let headers2 = {
            "Content-type": "application/json; charset=UTF-8",
            "Authorization": 'Bearer '+ obj
             };

        var datajson=JSON.stringify(date22.value)
        var timejson=JSON.stringify(oraalesa.value)
        var datadejson=JSON.parse(datajson)
        var timedejson=JSON.parse(timejson)
        axios.post("http://localhost:8080/appointment/save",{app_date: datadejson+'T'+timedejson,confirmed:0, hospital:{id:hosp.value}},{ headers: headers2})
              .then(response => {
      
          
               navigate("/myapp")
              })
              .catch(({ response }) => {
                  if (typeof response !== "undefined")
                      setMessage("Ceva nu a mers bine.");
      
              })
        
    }
    var maxDate=new Date();
    const onChange2 = (e) => {
        
        var x= new Date(e.target.value)
        if(x.getDay()==0){
        console.log(x.getDate())
        e.target.value=new Date(x.getFullYear(),x.getMonth(),x.getDate()+2).toISOString().split('T')[0]
        console.log(e.target.value)
        setDate(e.target.value)
        }
        else if(x.getDay()==6){
            console.log(x.getDate())
            e.target.value=new Date(x.getFullYear(),x.getMonth(),x.getDate()+3).toISOString().split('T')[0]
            console.log(e.target.value)
            setDate(e.target.value)
        }
        else
        {
            setDate(e.target.value)
        }
      }
      if(JSON.parse(localStorage.getItem("user")) && JSON.parse(localStorage.getItem("role"))[0].authority==="user")
    return (
        <div className="App2"  >    <NavbarUser></NavbarUser>
            <div style={{
            position:'absolute',
            display: 'block',
            width: 700,
            left:400
        }}>
            <h4>Adauga programare </h4>
            <Form  onSubmit= {handleSubmit}>
                <Form.Group>
                    <Form.Label>Selectați data programării {day}</Form.Label>
                    <Form.Control
                required
                type="date"
                name="date22"
                placeholder="Due date"
                value={data}
                onChange={(e) => onChange2(e)}
                min={new Date().toISOString().split('T')[0]}
                max={new Date(maxDate.getFullYear()+1,maxDate.getMonth()+1,maxDate.getDay()).toISOString().split('T')[0]}
              />
                </Form.Group>
                  <Form.Group>
                    <Form.Label>Alegeți Spitalul</Form.Label>
                    <Form.Select aria-label="Default select example" name= "hosp">
                    { data2.map((hospital) => (
                                   <option value={[hospital.id]}>{[hospital.name]}</option>
                               ))

                               }
                    </Form.Select>
                </Form.Group>
                <Button class="but" variant="danger" type="submit" style={{margin:"20px"}}>
                    Selectează data
                </Button>
            </Form>

            {vizibil && <Form onSubmit= {handleSubmit2}>
                  <Form.Group>
                    <Form.Label>Orele disponibile: </Form.Label>
                    <Form.Select aria-label="Default select example" name= "oraalesa">
                    { avh.map((x) => (
                                   <option>{x}</option>
                               ))

                               }
                    </Form.Select>
                </Form.Group>
                <Button class="but" variant="danger" type="submit" style={{margin:"20px"}}>
                    Adaugă programare
                </Button>
            </Form>}
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