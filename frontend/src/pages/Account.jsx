import { MDBContainer, MDBRow, MDBCol, MDBBtn } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";

import '../css/Account.css';

export default function Account() {
    const [isLoading, setIsLoading] = useState(true);
    const [userData, setUserData] = useState([]);
    const [cookies, setCookie, removeCookie] = useCookies(['token']);

    const navigate = useNavigate();

    useEffect(() => {
        checkLogin();
        setIsLoading(false);
    }, []);

    function checkLogin() {
        debugger;
        if (cookies.token === undefined) {
            navigate('/sign-in');
        }
        else {
            getUserData();
        }
    }

    async function getUserData() {
        const response = await fetch(`http://localhost:8080/get-user-data?token=${cookies.token}`);
        
        debugger;
        
        const data = await response.json();
        setUserData(data);
    }

    function logOut() {
        removeCookie("token");
        navigate('/sign-in');
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
                    <MDBCol>
                        {userData.phoneNumbers}
                    </MDBCol>
                    <input className="form-control data-input" label="Phone Number" min="1" max="13" minlength="9"></input>
                    <MDBCol>
                        <MDBBtn color="success">Add new phone number</MDBBtn>
                    </MDBCol>
                </article>
            </MDBRow>

            <MDBRow tag='dl'>
                <article className="d-flex align-items-center mb-2">
                    <MDBCol tag='dt' sm='3'>
                        E-Mail Addresses:
                    </MDBCol>
                    <MDBCol>
                        {userData.emails}
                    </MDBCol>
                    <input className="form-control data-input" label="E-Mail Address" min="1" max="13" minlength="9"></input>
                    <MDBCol>
                        <MDBBtn color="success">Add new e-mail address</MDBBtn>
                    </MDBCol>
                </article>
            </MDBRow>

            <MDBRow tag='dl'>
                <MDBCol tag='dt' sm='3'>
                    <MDBBtn color="success" onClick={logOut}>Log out</MDBBtn>
                </MDBCol>
            </MDBRow>
        </MDBContainer>
    </>)
}