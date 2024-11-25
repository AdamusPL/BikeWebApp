import { MDBContainer, MDBRow, MDBCol, MDBBtn } from "mdb-react-ui-kit";
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

    function addPhoneNumber() {

    }

    function addEmail() {

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

            <MDBRow tag='dl'>
                <article className="d-flex align-items-center mb-2">
                    <MDBCol tag='dt' sm='3'>
                        Phone Numbers:
                    </MDBCol>
                    <MDBCol tag='dd'>
                        {userData.phoneNumbers}
                    </MDBCol>
                    <input onChange={(e) => { setPhoneNumber(e.target.value) }} className="form-control data-input" label="Phone Number" min="1" max="13" minLength="9"></input>
                    <MDBCol>
                        <MDBBtn onClick={addPhoneNumber} className='classic-button'>Add new phone number</MDBBtn>
                    </MDBCol>
                </article>
            </MDBRow>

            <MDBRow tag='dl'>
                <article className="d-flex align-items-center mb-2">
                    <MDBCol tag='dt' sm='3'>
                        E-Mail Addresses:
                    </MDBCol>
                    <MDBCol tag='dd'>
                        {userData.emails}
                    </MDBCol>
                    <input onChange={(e) => { setEmail(e.target.value) }} className="form-control data-input" label="E-Mail Address" min="1" max="13" minLength="9"></input>
                    <MDBCol>
                        <MDBBtn onClick={addEmail} className='classic-button'>Add new e-mail address</MDBBtn>
                    </MDBCol>
                </article>
            </MDBRow>

            <MDBRow tag='dl'>
                <MDBCol tag='dt'>
                    <MDBBtn className='classic-button' onClick={logOut}>Log out</MDBBtn>
                </MDBCol>
            </MDBRow>
        </MDBContainer>
    </>)
}