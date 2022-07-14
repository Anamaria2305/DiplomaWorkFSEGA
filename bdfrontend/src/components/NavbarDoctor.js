import React from 'react';
import {
  Nav,
  NavLink,
  Bars,
  NavMenu,
  NavBtn,
  NavBtnLink,
} from './NavbarElements';
import { BiUserCircle } from "react-icons/bi";
const NavbarDoctor = () => {
  function logout() {
    localStorage.removeItem("user");
    localStorage.removeItem("role");

  }
    return (
      <>
        <Nav>
          <Bars />
          
          <NavBtn>
            <NavBtnLink to='/stock'>Vezi stocurile</NavBtnLink>
            <NavBtnLink to='/confirmed'>Programările confirmate</NavBtnLink>
            <NavBtnLink to='/unconfirmed'>Programările viitoare</NavBtnLink>
            <NavBtnLink to='/apeluri'>Adaugă cerere</NavBtnLink>
            <NavBtnLink to='/update'>Raport stoc</NavBtnLink>
            <NavBtnLink to= '/' onClick={()=>logout()}>Log Out</NavBtnLink>
            
          </NavBtn>
          <div  style={{marginTop:"20px"}}>
          <NavBtn >  
          Donează sânge, fii erou!
          <NavBtnLink to= '/doctorpage'> <BiUserCircle size={'2em'}/></NavBtnLink>
          </NavBtn>
          </div>
        </Nav>
      </>
    );
  };
    
  export default NavbarDoctor;