import { MDBBtn, MDBContainer, MDBInput, MDBRadio } from "mdb-react-ui-kit";
import logo from "../assets/logo.png";
import { useState } from "react";
import { Link } from "react-router-dom";

export default function Registration() {

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmedPassword, setConfirmedPassword] = useState("");
    const [email, setEmail] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [loginStatus, setLoginStatus] = useState("");
    const [selectedRole, setSelectedRole] = useState(false);

    function register() {
        const userData = {
            firstName: firstName,
            lastName: lastName,
            username: username,
            email: email,
            phoneNumber: phoneNumber,
            password: password,
            confirmedPassword: confirmedPassword,
            selectedRole: selectedRole
        };

        fetch(`http://localhost:8080/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userData)

        }).then(response => {
            if (response.ok) {
                setLoginStatus("User successfully registered! Now return to sign-in page to use your account.");
            }
            else {
                setLoginStatus("Error: Incorrect credentials");
            }
        })
    }

    const handleRadioButtonChange = (event) => {
        setSelectedRole(event.target.value);
    };


    return (<>
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

            <MDBContainer className="mt-3">
                <MDBRadio name='flexRadioDefault' id='client' label='Client' value={false} onChange={handleRadioButtonChange} defaultChecked />
                <MDBRadio name='flexRadioDefault' id='shop-assistant' label='Shop Assistant' value={true} onChange={handleRadioButtonChange} />
            </MDBContainer>

            <div className="d-flex justify-content-center">
                <MDBBtn className="mt-4" color="success" onClick={register}>Create an account</MDBBtn>
            </div>
            <p className="mt-4">{loginStatus}</p>
        </MDBContainer>
    </>)
}