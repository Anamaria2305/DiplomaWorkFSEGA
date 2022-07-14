import React, {useState,useEffect } from 'react';
import { Link,useNavigate} from 'react-router-dom';
import axios from 'axios'
import NavbarUser from '../components/NavbarUser';
import {Card,Row,Col,Button,Table} from 'react-bootstrap'
import Popup from '../components/Popup';
function Myapp() {

    const [data3, setData3] = useState(["Nu aveți programări"]);
    let navigate = useNavigate();
    const [isOpen2, setIsOpen2] = useState(false);
    const[bt,setBt]=useState([]);
    
   useEffect(() => {


        var obj = JSON.parse(localStorage.getItem('user'));
          
        
        let headers = {
            "Content-type": "application/json; charset=UTF-8",
            "Authorization": 'Bearer '+ obj
             };

            
        axios.get("http://localhost:8080/appointment/byUser",{ headers: headers})
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
    
    function fromintToButton( confirm,id,appointment){
        if(confirm == 0)
        return <Button variant="info" onClick={() => handleClick(id)} >Sterge programarea</Button> 
        else
        return <Button variant="success"  onClick={()=>togglePopup(appointment)} >Vezi Rezultatele</Button>
    }
    function fromBool(nr){
        if(nr == 1){
            return "Pozitiv"
        }
        else if (nr==0)
            return "Negativ"
        else 
            return("NAN")
    }
    function fromNr(nr){
        if(typeof nr !== 'undefined')
            return nr
        else 
            return ("NAN")
    }
    const togglePopup = (appointment) => {
        setIsOpen2(!isOpen2);
        setBt(appointment.bloodtests);
      }
    const handleClick = (nr) => {
        var obj = JSON.parse(localStorage.getItem('user'));

             const params = new URLSearchParams({
                id: nr,
            }).toString();

            const url="http://localhost:8080/appointment/delete?"+params
            let headers2 = {
                "Content-type": "application/json; charset=UTF-8",
                "Authorization": 'Bearer '+ obj
                 };
    
            axios.delete(url,{ headers: headers2})
                .then(res => {
                    window.location.reload()
                })
                .catch(err => {
                    console.log(err);
                })

            

      }
    


    if(JSON.parse(localStorage.getItem("user")) && JSON.parse(localStorage.getItem("role"))[0].authority==="user")
    return (
        <div className="App3">
            <NavbarUser />
            <h2>Programările dumneavoastră </h2>
           
         
            <div class="row row-cols-3 g-1">
            {data3.map((appointment) => (
            <Card bg='danger' style={{ width: '18rem',margin:'8px' }}>
            <Card.Header style={{fontWeight: "bold"}}>Spitalul: {appointment?.hospital?.name} <img src="fp.png" height='30px' style={{float:'right'}}></img></Card.Header>
            <Card.Img variant="top"  />
            <Card.Body>
                <Card.Title> {fromint(appointment?.confirmed)}</Card.Title>
                <Card.Text>
                Programarea este în data de:<br></br>
                 {toDate(appointment?.app_date)}<br></br>
                La ora: {toTime(appointment?.app_date)}<br></br>
                Adresa: {appointment?.hospital?.address} <br></br>
                Contact la nr: {appointment?.hospital?.phonenumber}
                </Card.Text>
                {fromintToButton(appointment?.confirmed,appointment?.id,appointment)}

            </Card.Body>
            </Card>
          
               ))}
             </div>
              

             {isOpen2 && <Popup
      content={<>

<Table variant='danger' bordered style={{marginBottom:'-10px'}}>
               
                <thead>
                    <th>Parametru Analizat</th>
                    <th>Valoarea</th>
                </thead>
                    <tbody>
                    <tr>
                  <td>Sida</td>
                  <td>{fromBool(bt?.sida)}</td>    
                  </tr>
                  <tr>
                  <td>Leucemie</td>
                  <td>{fromBool(bt?.leucemie)}</td>          
                  </tr>
                  <tr>
                  <td>Hepatia B</td>
                  <td>{fromBool(bt?.hepatitab)}</td>          
                  </tr>
                  <tr>
                  <td>Hepatia C</td>
                  <td>{fromBool(bt?.hepatitac)}</td>          
                  </tr>
                  <tr>
                  <td>Trombocite</td>
                  <td>{fromNr(bt?.trombocite)}</td>          
                  </tr>
                  <tr>
                  <td>Hemoglobina</td>
                  <td>{fromNr(bt?.hemoglobina)}</td>          
                  </tr>
                  <tr>
                  <td>Leucocite</td>
                  <td>{fromNr(bt?.leucocite)}</td>          
                  </tr>
                  <tr>
                  <td>Colesterol</td>
                  <td>{fromNr(bt?.colesterol)}</td>          
                  </tr>
                  </tbody>
                        
                    
                
            </Table>

      </>}
      handleClose={togglePopup}
    />}

        </div>
      )
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

export default Myapp;