import { MDBContainer, MDBRow, MDBCol, MDBBtn, MDBTypography, MDBInput } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";

import '../css/Account.css';

export default function Account() {
    const [userData, setUserData] = useState([]);
    const [cookies, removeCookie] = useCookies(['token']);

    const [email, setEmail] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");

    const [infoStatus, setInfoStatus] = useState("");

    const navigate = useNavigate();

    useEffect(() => {
        checkLogin();
    }, []);

    function checkLogin() {
        if (cookies.token === undefined) {
            navigate('/sign-in');
        }
        else {
            getUserData();
        }
    }

    async function getUserData() {
        const response = await fetch(`http://localhost:8080/get-user-data`, {
            credentials: 'include'
        });
        const data = await response.json();

        setUserData(data);
    }

    function logOut() {
        removeCookie("token");
        navigate('/sign-in');
    }

    function addPhoneNumber(newPhoneNumber) {
        const phoneNumberObj = {
            phoneNumber: newPhoneNumber
        }

        fetch(`http://localhost:8080/add-phone-number`, {
            credentials: 'include',
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(phoneNumberObj)
        }).then(response => {
            if (response.ok) {
                setUserData({
                    ...userData,
                    phoneNumbers: userData.phoneNumbers + ", " + newPhoneNumber
                });
            }
            return response.text();
        }).then(data => {
            setInfoStatus(data);
        })
    }

    function addEmail(newEmail) {
        fetch(`http://localhost:8080/add-email?email=${newEmail}`, {
            credentials: 'include',
            method: 'POST'
        }).then(response => {
            if (response.ok) {
                setUserData({
                    ...userData,
                    emails: userData.emails + ", " + newEmail
                });
            }
            return response.text();
        }).then(data => {
            setInfoStatus(data);
        })
    }

    return (<>
        <MDBContainer className="mt-4">
            <MDBRow tag='dl'>
                <MDBCol tag='dt' sm='3'>
                    Username:
                </MDBCol>
                <MDBCol tag='dd' sm='9'>
                    {userData.username}
                </MDBCol>
            </MDBRow>

            <MDBRow tag='dl'>
                <MDBCol tag='dt' sm='3'>
                    Name:
                </MDBCol>
                <MDBCol tag='dd' sm='9'>
                    {userData.fullname}
                </MDBCol>
            </MDBRow>

            <MDBRow className="data-with-fields mb-2">
                <MDBCol>
                    <MDBTypography tag='strong'>Phone Numbers:</MDBTypography>
                </MDBCol>
                <MDBCol>
                    {userData.phoneNumbers}
                </MDBCol>
                <MDBCol>
                    <MDBInput onChange={(e) => { setPhoneNumber(e.target.value) }} className="form-control data-input" label="Phone Number*" type="tel" maxLength="13" minLength="9"></MDBInput>
                </MDBCol>
                <MDBCol>
                    <MDBBtn onClick={() => addPhoneNumber(phoneNumber)} className='classic-button'>Add new phone number</MDBBtn>
                </MDBCol>
            </MDBRow>

            <MDBRow className="data-with-fields mb-2">
                <MDBCol>
                    <MDBTypography tag='strong'>E-Mail Addresses:</MDBTypography>
                </MDBCol>
                <MDBCol>
                    {userData.emails}
                </MDBCol>
                <MDBCol>
                    <MDBInput onChange={(e) => { setEmail(e.target.value) }} className="form-control data-input" label="E-Mail Address*" type="email" maxLength="64" minLength="1"></MDBInput>
                </MDBCol>
                <MDBCol>
                    <MDBBtn onClick={() => addEmail(email)} className='classic-button'>Add new e-mail address</MDBBtn>
                </MDBCol>
            </MDBRow>

            <MDBRow tag='dl'>
                <MDBCol tag='dt'>
                    <MDBBtn className='classic-button' onClick={logOut}>Log out</MDBBtn>
                </MDBCol>
            </MDBRow>

            <div>
                <MDBTypography id="info" tag='strong'>{infoStatus}</MDBTypography>
            </div>
        </MDBContainer>
    </>)
}