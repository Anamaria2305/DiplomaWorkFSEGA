import React from 'react';
import Navbar from '../components/Navbar';
import NavbarUser from '../components/NavbarUser';
import SimpleImageSlider from "react-simple-image-slider";
function About() {

    function seeNav(obj) {
        if (JSON.parse(localStorage.getItem("user")))
            obj = JSON.parse(localStorage.getItem("role"))[0].authority
        else
            obj = "logout"

        if (obj === "user")
            return <NavbarUser />
        else
            return <Navbar />

    }
    const images = [
      { url: "../doneaza.jpg" },
      { url: "../dece.jpg" },
      { url: "../admisibil.jpg" },
      { url: "../dupa.jpg" }
    ];
      
    return (
        <div>
            {seeNav("logout")}
      
      
      <SimpleImageSlider
        width={'100%'}
        height={'650px'}
        images={images}
        showBullets={true}
        showNavs={true}  
      />
    
        </div>
    );

}

export default About;