import { MDBBtn, MDBContainer, MDBInput, MDBRadio } from "mdb-react-ui-kit";
import logo from "../assets/logo.png";
import { useState } from "react";

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
            return response.text()
        }).then((data) => {
            setLoginStatus(data);
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
            <MDBInput label="First Name" id="typeText" type="text" className="mt-5" minLength={2} maxLength={18} pattern="^[A-Za-z]+([-' ][A-Za-z]+)*$" required onChange={(e) => { setFirstName(e.target.value) }} />
            <MDBInput label="Last Name" id="typeText" type="text" className="mt-3" minLength={2} maxLength={48} pattern="^[A-Za-z]+([-' ][A-Za-z]+)*$" required onChange={(e) => { setLastName(e.target.value) }} />

            <MDBInput label="Username" id="typeText" type="text" className="mt-5" minLength={6} maxLength={30} pattern="^[A-Za-z0-9_.]+$" required onChange={(e) => { setUsername(e.target.value) }} />
            <MDBInput label="E-mail Address" id="typeEmail" type="email" className="mt-3" minLength={3} maxLength={64} pattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$" required onChange={(e) => { setEmail(e.target.value) }} />
            <MDBInput label="Phone Number" id="typePhone" type="tel" className="mt-3" minLength={9} maxLength={13} pattern="^\+?\d+$" required onChange={(e) => { setPhoneNumber(e.target.value) }} />

            <MDBInput label="Password" id="typePassword" type="password" className="mt-5" minLength={8} maxLength={128} required onChange={(e) => { setPassword(e.target.value) }} />
            <MDBInput label="Confirm Password" id="typePassword" type="password" className="mt-3" minLength={8} maxLength={128} required onChange={(e) => { setConfirmedPassword(e.target.value) }} />

            <MDBContainer className="mt-3">
                <MDBRadio name='flexRadioDefault' id='client' label='Customer' value={false} onChange={handleRadioButtonChange} defaultChecked />
                <MDBRadio name='flexRadioDefault' id='shop-assistant' label='Shop Assistant' value={true} onChange={handleRadioButtonChange} />
            </MDBContainer>

            <div className="d-flex justify-content-center">
                <MDBBtn className="mt-4" style={{ backgroundColor: "#002E80" }} onClick={register}>Create an account</MDBBtn>
            </div>
            <p className="mt-4">{loginStatus}</p>
        </MDBContainer>
    </>)
}