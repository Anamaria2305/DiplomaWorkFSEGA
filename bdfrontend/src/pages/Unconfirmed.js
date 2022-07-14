import React, {useState,useEffect } from 'react';
import { Link,useNavigate} from 'react-router-dom';
import axios from 'axios'
import NavbarDoctor from '../components/NavbarDoctor';
import {Card,Button} from 'react-bootstrap'
function Unconfirmed() {

    let navigate = useNavigate();
    const [data3, setData3] = useState(["Nu aveți programări"]);
 
   useEffect(() => {


        var obj = JSON.parse(localStorage.getItem('user'));
          
        
        let headers2 = {
            "Content-type": "application/json; charset=UTF-8",
            "Authorization": 'Bearer '+ obj
             };

        axios.get("http://localhost:8080/appointment/unconfirmed",{ headers: headers2})
            .then(res => {
                setData3(res.data);
                
            })
            .catch(err => {
                console.log(err);
            })
    }, [])

    function fromint(nr){
        if(nr == 1){
            return "Confirmată."
        }
        else
            return "Neconfirmată."
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

            const url="http://localhost:8080/appointment/chconfirmed?"+params

            let headers2 = {
                "Content-type": "application/json; charset=UTF-8",
                "Authorization": 'Bearer '+ obj
                 };
    
            axios.get(url,{ headers: headers2})
                .then(res => {
                    navigate('/confirmed')
                })
                .catch(err => {
                    console.log(err);
                })

            

      }
    

    if(JSON.parse(localStorage.getItem("user")) && JSON.parse(localStorage.getItem("role"))[0].authority==="doctor")
    return (
        <div className="App3">
            <NavbarDoctor />
            <h2>Programările neconfirmate din spitalul dumneavoastră </h2>
           
         
            <div class="row row-cols-3 g-1">
            {data3.map((appointment) => (
            <Card bg='danger' style={{ width: '18rem',margin:'8px' }}>
            <Card.Header style={{fontWeight: "bold"}}>Donator: {appointment?.users?.name} <img src="fp.png" height='30px' style={{float:'right'}}></img></Card.Header>
            <Card.Img variant="top"  />
            <Card.Body>
                <Card.Title> {fromint(appointment?.confirmed)} <br></br>Spitalul:{appointment?.hospital?.name} </Card.Title>
                <Card.Text>
                Programarea este în data de:<br></br>
                 {toDate(appointment?.app_date)}<br></br>
                La ora: {toTime(appointment?.app_date)}<br></br>
                Adresa: {appointment?.hospital?.address} <br></br>
                Contact la nr: {appointment?.hospital?.phonenumber}
                </Card.Text>
                <Button variant="info"  onClick={() => handleClick(appointment.id)}  >Confirma</Button>
            </Card.Body>
            </Card>
          
               ))}
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

export default Unconfirmed;