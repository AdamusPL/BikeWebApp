import { MDBContainer, MDBRow, MDBCol, MDBBtn, MDBTypography } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";

import '../css/Account.css';

export default function Account() {
    const [isLoading, setIsLoading] = useState(true);
    const [userData, setUserData] = useState([]);
    const [cookies, setCookie, removeCookie] = useCookies(['token']);

    const [email, setEmail] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");

    const navigate = useNavigate();

    useEffect(() => {
        checkLogin();
        setIsLoading(false);
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
        debugger;
        fetch(`http://localhost:8080/add-phone-number?phoneNumber=${newPhoneNumber}`, {
            credentials: 'include',
            method: 'POST'
        }).then(response => {
            if (response.ok) {
                setUserData({
                    ...userData,
                    phoneNumbers: userData.phoneNumbers + ", " + newPhoneNumber
                });
            }
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
                    <input onChange={(e) => { setPhoneNumber(e.target.value) }} className="form-control data-input" label="Phone Number" min="1" max="13" minLength="9"></input>
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
                    <input onChange={(e) => { setEmail(e.target.value) }} className="form-control data-input" label="E-Mail Address" min="1" max="13" minLength="9"></input>
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
        </MDBContainer>
    </>)
}