import { useState } from "react";
import logo from '../assets/logo.png'
import '../css/Layout.css'
import CookieModal from "./CookieModal";
import { MDBIcon } from "mdb-react-ui-kit";

export function Layout({ children }) {
    const [openBasic, setOpenBasic] = useState(false);

    return (
        <>
            <nav class="navbar navbar-expand-lg bg-success navbar-dark ">
                <div class="container-fluid">

                    <a class="navbar-brand" href="/"><img id="logo" src={logo} /></a>

                    <button class="navbar-toggler" type="button" data-mdb-toggle="collapse" data-mdb-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <i class="fas fa-bars navbar-elem"></i>
                    </button>

                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link" href="/"><MDBIcon fas icon="home" className="navbar-elem" /></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link navbar-elem" href="/bike-shop">Bikes</a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link navbar-elem" href="/part-shop">Parts</a>
                            </li>

                        </ul>

                        <ul class="navbar-nav d-flex flex-row me-1">
                            <li class="nav-item me-3 me-lg-0">
                                <a class="nav-link" href="/order-list"><MDBIcon fas icon="envelope" className="navbar-elem" /></a>
                            </li>
                            <li class="nav-item me-3 me-lg-0">
                                <a class="nav-link" href="/cart"><MDBIcon fas icon="shopping-cart" className="navbar-elem" /></a>
                            </li>
                            <li class="nav-item me-3 me-lg-0">
                                <a class="nav-link" href="/account"><MDBIcon fas icon="user-alt" className="navbar-elem" /></a>
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