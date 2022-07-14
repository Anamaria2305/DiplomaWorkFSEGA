import React from 'react';
import {
  Nav,
  NavLink,
  Bars,
  NavMenu,
  NavBtn,
  NavBtnLink,
} from './NavbarElements';

const Navbar = () => {

    return (
      <>
        <Nav>
          <Bars />
          
          <NavBtn>
            <NavBtnLink  to='/'>Acasă</NavBtnLink>
            <NavBtnLink to='/about'>Despre</NavBtnLink>
            <NavBtnLink to='/login'>Log In</NavBtnLink>
            <NavBtnLink to='/register'>Înregistrează-te</NavBtnLink>
            <NavBtnLink to='/stock'>Vezi stocurile</NavBtnLink>
          
          </NavBtn>
         <div   style={{marginTop:"20px",marginRight:"20px"}}>Donează sânge, fii erou!</div>
        </Nav>
      </>
    );
  };
    
  export default Navbar;