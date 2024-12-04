import { MDBBtn, MDBContainer, MDBInput, MDBTypography } from "mdb-react-ui-kit";
import logo from "../assets/logo.png";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useCookies } from 'react-cookie';

import '../css/SignIn.css';

export default function SignIn() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [loginStatus, setLoginStatus] = useState("");
    const [cookies, setCookie] = useCookies(['token']);

    const navigate = useNavigate();

    async function signIn() {
        const userData = {
            username: username,
            password: password
        };

        fetch(`http://localhost:8080/sign-in`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)

        }).then(response => {
            debugger;
            if (response.status === 401) {
                return response.text();
            }
            else {
                return response.json();
            }
        }
        )
            .then(data => {
                debugger;
                if (typeof data !== 'string') {
                    if (data.accessToken !== null) {
                        setCookie('token', data.accessToken, { path: '/' });
                        navigate('/');
                    }
                    else {
                        setLoginStatus("Error: Wrong username or password");
                    }
                }
                else{
                    setLoginStatus(data);
                }
            })
    }

    return (
        <MDBContainer className="w-25">
            <figure className='figure'>
                <img
                    src={logo}
                    className='figure-img img-fluid rounded shadow-3 mb-3 mt-5'
                    alt='...'
                />
                <figcaption className='figure-caption d-flex justify-content-center'>Let's start your story with Bike Paradise!</figcaption>
            </figure>
            <MDBInput label="Username" id="typeText" type="text" className="mt-5" onChange={(e) => { setUsername(e.target.value) }} />
            <MDBInput label="Password" id="typePassword" type="password" className="mt-3" onChange={(e) => { setPassword(e.target.value) }} />
            <div className="d-flex justify-content-center">
                <MDBBtn aria-label="Sign-in to account" className="mt-4" style={{ backgroundColor: "#002E80" }} onClick={signIn}>Sign-in</MDBBtn>
            </div>
            <p className="mt-5">Don't have an account? Register <Link color="#002E80" style={{ fontWeight: 'bold' }} to='/register'>here</Link></p>
            <MDBTypography id="info" tag='strong' className="mt-5">{loginStatus}</MDBTypography>
        </MDBContainer>
    );
}