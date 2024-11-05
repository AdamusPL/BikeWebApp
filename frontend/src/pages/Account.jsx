import { MDBContainer, MDBRow, MDBCol, MDBBtn } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Account() {
    const [isLoading, setIsLoading] = useState(true);
    const [userData, setUserData] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        checkLogin();
        setIsLoading(false);
    }, []);

    function checkLogin() {
        debugger;
        if (localStorage.getItem('token') === null) {
            navigate('/sign-in');
        }
        else {
            getUserData();
        }
    }

    function getUserData() {
        fetch(`http://localhost:8080/get-user-data`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
            .then(data => {
                debugger;
                console.log(data);
                setUserData(data);
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

            <MDBRow tag='dl'>
                <article className="d-flex align-items-center mb-2">
                    <MDBCol tag='dt' sm='3'>
                        Phone Numbers:
                    </MDBCol>
                    <MDBCol tag='dd' sm='9'>
                        {userData.phoneNumbers}
                    </MDBCol>
                    <input className="form-control" label="Phone Number" min="1" max="13" minlength="9"></input>
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
                    <MDBCol tag='dd' sm='9'>
                        {userData.emails}
                    </MDBCol>
                    <input className="form-control" label="E-Mail Address" min="1" max="13" minlength="9"></input>
                    <MDBCol>
                        <MDBBtn color="success">Add new e-mail address</MDBBtn>
                    </MDBCol>
                </article>
            </MDBRow>
        </MDBContainer>
    </>)
}