import { MDBBtn, MDBContainer, MDBInput } from "mdb-react-ui-kit";
import logo from "../assets/logo.png";
import { useState } from "react";
import { Link } from "react-router-dom";

export default function Registration(){

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmedPassword, setConfirmedPassword] = useState("");
    const [email, setEmail] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [loginStatus, setLoginStatus] = useState("");

    function register(){
        const userData = {
            firstName: firstName,
            lastName: lastName,
            username: username,
            email: email,
            phoneNumber: phoneNumber,
            password: password,
            confirmedPassword: confirmedPassword
        };

        fetch(`http://localhost:8080/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userData)

        }).then(response => {
            if (response.ok) {
                return <Redirect to='/' />
            }
            else{
                setLoginStatus("Error: Incorrect credentials");
            }
        })
    }

    return(<>
        <MDBContainer className="w-25">
            <figure className='figure'>
                <img
                    src={logo}
                    className='figure-img img-fluid rounded shadow-3 mb-3 mt-5'
                    alt='...'
                />
                <figcaption className='figure-caption d-flex justify-content-center'>Let's start your story with Bike Paradise!</figcaption>
            </figure>
            <MDBInput label="First Name" id="typeText" type="text" className="mt-5" onChange={(e) => { setFirstName(e.target.value) }} />
            <MDBInput label="Last Name" id="typeText" type="text" className="mt-3" onChange={(e) => { setLastName(e.target.value) }} />

            <MDBInput label="Username" id="typeText" type="text" className="mt-5" onChange={(e) => { setUsername(e.target.value) }} />
            <MDBInput label="E-mail Address" id="typeEmail" type="email" className="mt-3" onChange={(e) => { setEmail(e.target.value) }} />
            <MDBInput label="Phone Number" id="typePhone" type="tel" className="mt-3" onChange={(e) => { setPhoneNumber(e.target.value) }} />

            <MDBInput label="Password" id="typePassword" type="password" className="mt-5" onChange={(e) => { setPassword(e.target.value) }} />
            <MDBInput label="Confirm Password" id="typePassword" type="password" className="mt-3" onChange={(e) => { setConfirmedPassword(e.target.value) }} />
            <div className="d-flex justify-content-center">
                <MDBBtn className="mt-4" color="success" onClick={register}>Create an account</MDBBtn>
            </div>
            <p className="mt-5">{loginStatus}</p>
        </MDBContainer>
    </>)
}