import React, {useState,useEffect } from 'react';
import { Link} from 'react-router-dom';
import axios from 'axios'
import NavbarDoctor from '../components/NavbarDoctor';
import {Card,Button,Form,Table} from 'react-bootstrap'
import Popup from '../components/Popup';
 
function Confirmedprog() {

    const [data3, setData3] = useState(["Nu aveți programări"]);
    const [errorMessage, setMessage] = useState("");
    const[appid,setAppId]=useState();
    const [isOpen, setIsOpen] = useState(false);
    const [isOpen2, setIsOpen2] = useState(false);
    const[bt,setBt]=useState([]);
    const togglePopup = (id) => {
    setIsOpen(!isOpen);
    setAppId(id);
  }
  const togglePopup2 = (appointment) => {
    setIsOpen2(!isOpen2);
    setBt(appointment.bloodtests);
  }
   useEffect(() => {


        var obj = JSON.parse(localStorage.getItem('user'));
          
        
        let headers2 = {
            "Content-type": "application/json; charset=UTF-8",
            "Authorization": 'Bearer '+ obj
             };
        axios.get("http://localhost:8080/appointment/confirmed",{ headers: headers2})
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

    function fromBool(nr){
        if(nr == 1){
            return "Pozitiv"
        }
        else
            return "Negativ"
    }

    function toDate(date){
        return date?.split('T')[0]
    }
    function toTime(date){
        return date?.split('T')[1].substring(0,5)
    }
    function bloodtestButton(appointment){

        if (appointment.bloodtests)
        return   <Button variant="success"  onClick={()=>togglePopup2(appointment)}>Vezi Rezultatele</Button>
        else
        return <Button variant="info"  onClick={()=>togglePopup(appointment?.id)} >Adauga Analize</Button>
    }
   async function handleSubmit(event) {

        var { sida,leucemie, hb,hc,tr,le,hem,col} = document.forms[0];
        const params = new URLSearchParams({
            id: appid
          }).toString();
          var obj = JSON.parse(localStorage.getItem('user'));
          let headers = {
                  "Content-type": "application/json; charset=UTF-8",
                  "Authorization": 'Bearer '+ obj
           };

           try {
            var response= await axios.post("http://localhost:8080/bloodtest/save?"+params,
            {sida:sida.value, leucemie: leucemie.value, hepatitab: hb.value,
                hepatitac:hc.value,trombocite:tr.value,hemoglobina:hem.value,colesterol:col.value,
                leucocite:le.value},
                {headers: headers}
                )
            togglePopup(appid)
          } catch (error) {
            
            setMessage("Completati valori potrivite peste tot!");
            
          }

    }
    if(JSON.parse(localStorage.getItem("user")) && JSON.parse(localStorage.getItem("role"))[0].authority==="doctor")
    return (
        <div className="App3">
            <NavbarDoctor />
            <h2>Programările confirmate din spitalul dumneavoastră </h2>
           
         
            <div class="row row-cols-3 g-1">
            {data3.map((appointment) => (
            <Card bg='danger' style={{ width: '18rem',margin:'8px' }}>
            <Card.Header style={{fontWeight: "bold"}}>Spitalul: {appointment?.hospital?.name}<br></br>Donator: {appointment?.users?.name}  <img src="fp.png" height='30px' style={{float:'right'}}></img></Card.Header>
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
                {bloodtestButton(appointment)}
            </Card.Body>
            </Card>
          
               ))}
             </div>
             {isOpen && <Popup
      content={<>

        <Form style={{fontSize:13}} onSubmit= {handleSubmit}>
            <Form.Group>
                    <Form.Label>SIDA</Form.Label>
                    <Form.Select aria-label="Default select example" name= "sida">
                    <option value='0'>Nu</option>
                    <option value='1'>Da</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group>
                    <Form.Label>LEUCEMIE</Form.Label>
                    <Form.Select aria-label="Default select example" name= "leucemie">
                    <option value='0'>Nu</option>
                    <option value='1'>Da</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group>
                    <Form.Label>HEPATITA B</Form.Label>
                    <Form.Select aria-label="Default select example" name= "hb">
                    <option value='0'>Nu</option>
                    <option value='1'>Da</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group>
                    <Form.Label>HEPATITA C</Form.Label>
                    <Form.Select aria-label="Default select example" name= "hc">
                    <option value='0'>Nu</option>
                    <option value='1'>Da</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group>
                    <Form.Label>TROMBOCITE</Form.Label>
                    <Form.Control type="number"  step="0.1" required name= "tr" onChange={(event) =>
                                event.target.value < 0
                                    ? (event.target.value = 0)
                                    : event.target.value
                            } />
                </Form.Group>
                <Form.Group>
                    <Form.Label>LEUCOCITE</Form.Label>
                    <Form.Control type="number"  step="0.1" required name= "le" onChange={(event) =>
                                event.target.value < 0
                                    ? (event.target.value = 0)
                                    : event.target.value
                            }/>
                </Form.Group>
                <Form.Group>
                    <Form.Label>HEMOGLOBINA</Form.Label>
                    <Form.Control type="number"  step="0.1" required name= "hem" onChange={(event) =>
                                event.target.value < 0
                                    ? (event.target.value = 0)
                                    : event.target.value
                            } />
                </Form.Group>
                <Form.Group>
                    <Form.Label>COLESTEROL</Form.Label>
                    <Form.Control type="number"  step="0.1" required name= "col" onChange={(event) =>
                                event.target.value < 0
                                    ? (event.target.value = 0)
                                    : event.target.value
                            }/>
                </Form.Group>
                <Form.Group>
                    <Form.Label style={{fontWeight: "bold",fontSize:15}}>{errorMessage}</Form.Label>
                </Form.Group>
                <Button class="but" variant="danger" type="submit" style={{margin:"8px"}}>
                    Salveaza
                </Button>
            </Form>

      </>}
      handleClose={togglePopup}
    />}
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
                  <td>{fromBool(bt.sida)}</td>    
                  </tr>
                  <tr>
                  <td>Leucemie</td>
                  <td>{fromBool(bt.leucemie)}</td>          
                  </tr>
                  <tr>
                  <td>Hepatia B</td>
                  <td>{fromBool(bt.hepatitab)}</td>          
                  </tr>
                  <tr>
                  <td>Hepatia C</td>
                  <td>{fromBool(bt.hepatitac)}</td>          
                  </tr>
                  <tr>
                  <td>Trombocite</td>
                  <td>{bt.trombocite}</td>          
                  </tr>
                  <tr>
                  <td>Hemoglobina</td>
                  <td>{bt.hemoglobina}</td>          
                  </tr>
                  <tr>
                  <td>Leucocite</td>
                  <td>{bt.leucocite}</td>          
                  </tr>
                  <tr>
                  <td>Colesterol</td>
                  <td>{bt.colesterol}</td>          
                  </tr>
                  </tbody>
                        
                    
                
            </Table>

      </>}
      handleClose={togglePopup2}
    />}

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

export default Confirmedprog;