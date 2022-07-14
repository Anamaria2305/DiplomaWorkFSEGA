import React, { Component, useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import axios from 'axios'
import Navbar from '../components/Navbar';
import NavbarUser from '../components/NavbarUser';
import NavbarDoctor from '../components/NavbarDoctor';
import NavAdmin from '../components/NavAdmin';
import { Table } from 'react-bootstrap';
function Stock() {

    const [data, setData] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8080/hospital/all")
            .then(res => {
                setData(res.data);
            })
            .catch(err => {
                console.log(err);
            })
    }, [])
    function quantity(Object) {

        if (typeof Object === 'undefined')

            return <td>

            </td>
        else


            return <td>{
                Object.quantity.toFixed(2)
            } litri</td>

    }
    function blood(Object) {

        if (typeof Object === 'undefined')

            return <td>

            </td>

        else





            return <td>{Object.bloodtype.blood}, {Object.bloodtype.rh}</td>



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
        else if(obj==="admin")
        return <NavAdmin/>
        else
        return <Navbar/>
       
    }
    return (
        <div>
            {seeNav("logout")}
            <Table variant='danger' bordered style={{marginBottom:'-10px'}}>
                <thead >
                    <tr>
                        <th>#</th>
                        <th>Nume</th>
                        <th>Adresa</th>
                        <th>Contact</th>
                        <th>Grupa de sange</th>
                        <th>Cantitatea</th>
                    </tr>
                </thead>
            
                    {data.map((hospital) => (
                      
                    <tbody>
                    <tr>
                            <td rowspan='8'>{hospital.id}</td>
                            <td rowspan='8'>{hospital.name}</td>
                            <td rowspan='8'>{hospital.address}</td>
                            <td rowspan='8'>{hospital.phonenumber}</td>
                            <td>{blood(hospital.stockList[0])}</td>
                            <td>{quantity(hospital.stockList[0]) }</td>
                        </tr>
                        <tr>
                        <td>{blood(hospital.stockList[1])}</td>
                        <td>{ quantity(hospital.stockList[1])}</td>
                        </tr>
                        <tr>
                        <td>{blood(hospital.stockList[2])}</td>
                        <td>{ quantity(hospital.stockList[2])}</td>
                        </tr>
                        <tr>
                        <td>{blood(hospital.stockList[3])}</td>
                        <td>{ quantity(hospital.stockList[3])}</td>
                        </tr>
                        <tr>
                        <td>{blood(hospital.stockList[4])}</td>
                        <td>{ quantity(hospital.stockList[4])}</td>
                        </tr>
                        <tr>
                        <td>{blood(hospital.stockList[5])}</td>
                        <td>{ quantity(hospital.stockList[5])}</td>
                        </tr>
                        <tr>
                        <td>{blood(hospital.stockList[6])}</td>
                        <td>{ quantity(hospital.stockList[6])}</td>
                        </tr>
                        <tr>
                        <td>{blood(hospital.stockList[7])}</td>
                        <td>{ quantity(hospital.stockList[7])}</td>
                        </tr>
                </tbody>
                        
                    ))}
                
            </Table>
        </div>
    );

}

export default Stock;