import { MDBBtn, MDBContainer, MDBInput } from "mdb-react-ui-kit";
import logo from "../assets/logo.png";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useCookies } from 'react-cookie';

export default function SignIn() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [loginStatus, setLoginStatus] = useState("");
    const [cookies, setCookie] = useCookies(['token']);

    const navigate = useNavigate();

    async function signIn() {
        debugger;

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
            if(response.ok){
                response.json();
            }
            else{
                setLoginStatus("Error: Incorrect credentials");
            }
        })
            .then(data => {
                if (data.accessToken) {
                    setCookie('token', data.accessToken, {path: '/'});
                    navigate('/');
                }
                else {
                    
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
                <MDBBtn className="mt-4" color="success" onClick={signIn}>Sign-in</MDBBtn>
            </div>
            <p className="mt-5">Don't have an account? Register <Link to='/register'>here</Link></p>
            <p className="mt-5">{loginStatus}</p>
        </MDBContainer>
    );
}