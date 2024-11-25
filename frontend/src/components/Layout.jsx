import { useState } from "react";
import logo from '../assets/logo.png'
import '../css/Layout.css'
import CookieModal from "./CookieModal";
import { MDBIcon } from "mdb-react-ui-kit";
import { useRole } from "./RoleProvider";

export function Layout({ children }) {
    const [openBasic, setOpenBasic] = useState(false);
    const { isLoggedIn, isShopAssistant } = useRole();

    const isActive = (path) => location.pathname === path ? "active" : "";

    return (
        <>
            <nav style={{backgroundColor: "#D9D9D9"}} className="navbar navbar-expand-lg navbar-dark">
                <div className="container-fluid">

                    <a className="navbar-brand" href="/"><img alt="logo" id="logo" src={logo} /></a>

                    <button className="navbar-toggler" type="button" data-mdb-toggle="collapse" data-mdb-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <i className="fas fa-bars navbar-elem"></i>
                    </button>

                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                            <li className="nav-item">
                                <a className="nav-link" aria-label="Home page" href="/"><MDBIcon fas icon="home" className={`${isActive('/') ? "active-elem" : "navbar-elem"}`} /></a>
                            </li>
                            <li className="nav-item">
                                <a className={`nav-link ${isActive('/bike-shop') ? "active-elem" : "navbar-elem"}`} aria-label="Bike shop" href="/bike-shop">Bikes</a>
                            </li>

                            <li className="nav-item">
                                <a className={`nav-link ${isActive('/part-shop') ? "active-elem" : "navbar-elem"}`} aria-label="Part shop" href="/part-shop">Parts</a>
                            </li>

                        </ul>

                        <ul className="navbar-nav d-flex flex-row me-1">
                            {isLoggedIn ?
                                <li className="nav-item me-3 me-lg-0">
                                    <a className="nav-link" aria-label="Order list" href="/order-list"><MDBIcon fas icon="envelope" className={`${isActive('/order-list') ? "active-elem" : "navbar-elem"}`} /></a>
                                </li>
                                : null}
                            {!isShopAssistant ?
                                <li className="nav-item me-3 me-lg-0">
                                    <a className="nav-link" aria-label="Shopping cart" href="/cart"><MDBIcon fas icon="shopping-cart" className={`${isActive('/cart') ? "active-elem" : "navbar-elem"}`} /></a>
                                </li>
                                : null}
                            <li className="nav-item me-3 me-lg-0">
                                <a className="nav-link" aria-label="Account" href="/account"><MDBIcon fas icon="user-alt" className={`${isActive('/account') || isActive('/sign-in') || isActive('/register') ? "active-elem" : "navbar-elem"}`} /></a>
                            </li>
                        </ul>

                    </div>
                </div>
            </nav>

            <main>
                {children}
            </main>
            <CookieModal />
        </>
    );
}