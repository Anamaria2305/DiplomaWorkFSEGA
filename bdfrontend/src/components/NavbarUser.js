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
const NavbarUser = () => {
  function logout() {
    localStorage.removeItem("user");
    localStorage.removeItem("role");
    localStorage.removeItem("user");
  }
    return (
      <>
        <Nav>
          <Bars />
          
          <NavBtn>
            <NavBtnLink to='/about'>Despre</NavBtnLink>
            <NavBtnLink to='/stock'>Vezi stocurile</NavBtnLink>
            <NavBtnLink to='/myapp'>Programările mele</NavBtnLink>
            <NavBtnLink to='/addprog'>Adauga Programare</NavBtnLink>
            <NavBtnLink to='/apeluri'>Apeluri</NavBtnLink>
            <NavBtnLink to= '/' onClick={()=>logout()}>Log Out</NavBtnLink>
          </NavBtn>
          <div  style={{marginTop:"20px"}}>
          <NavBtn >  
          Donează sânge, fii erou!
          <NavBtnLink to= '/userpage'> <BiUserCircle size={'2em'}/></NavBtnLink>
          </NavBtn>
          </div>
        </Nav>
      </>
    );
  };
    
  export default NavbarUser;