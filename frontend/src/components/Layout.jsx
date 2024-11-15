import { useState } from "react";
import {
    MDBContainer,
    MDBNavbar,
    MDBNavbarToggler,
    MDBIcon,
    MDBNavbarNav,
    MDBNavbarItem,
    MDBNavbarLink,
    MDBCollapse,
} from 'mdb-react-ui-kit';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHouse, faEnvelope, faCartShopping } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import logo from '../assets/logo.png'
import '../css/Layout.css'
import CookieModal from "./CookieModal";

export function Layout({ children }) {
    const [openBasic, setOpenBasic] = useState(false);

    return (
        <>
            <MDBNavbar expand='lg' light bgColor='success'>
                <MDBContainer fluid>
                    <Link to="/"><img id='logo' src={logo} /></Link>

                    <MDBNavbarToggler
                        aria-controls='navbarSupportedContent'
                        aria-expanded='false'
                        aria-label='Toggle navigation'
                        onClick={() => setOpenBasic(!openBasic)}
                    >
                        <MDBIcon icon='bars' fas />
                    </MDBNavbarToggler>

                    <MDBCollapse navbar open={openBasic} className="w-100">
                        <MDBNavbarNav className='mb-2 mb-lg-0'>
                            <MDBNavbarItem>
                                <MDBNavbarLink href='/' style={{ color: 'white' }}>
                                    <FontAwesomeIcon icon={faHouse} />
                                </MDBNavbarLink>
                            </MDBNavbarItem>

                            <MDBNavbarItem>
                                <MDBNavbarLink href='/bike-shop' style={{ color: 'white' }}>Bikes</MDBNavbarLink>
                            </MDBNavbarItem>

                            <MDBNavbarItem>
                                <MDBNavbarLink href='/part-shop' style={{ color: 'white' }}>Parts</MDBNavbarLink>
                            </MDBNavbarItem>

                            <MDBNavbarItem>
                                <MDBNavbarLink href='/order-list' style={{ color: 'white' }}>
                                    <FontAwesomeIcon icon={faEnvelope} />
                                </MDBNavbarLink>
                            </MDBNavbarItem>

                            <MDBNavbarItem>
                                <MDBNavbarLink href='/cart' style={{ color: 'white' }}>
                                    <FontAwesomeIcon icon={faCartShopping} />
                                </MDBNavbarLink>
                            </MDBNavbarItem>
                            
                            <MDBNavbarItem>
                                <MDBNavbarLink href='/account' style={{ color: 'white' }}>
                                    <MDBIcon fas icon="user-alt" />
                                </MDBNavbarLink>
                            </MDBNavbarItem>
                        </MDBNavbarNav>

                    </MDBCollapse>
                </MDBContainer>
            </MDBNavbar>
            <main>
                {children}
            </main>
            <CookieModal />
        </>
    );
}