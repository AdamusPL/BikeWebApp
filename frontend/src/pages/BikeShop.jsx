import React, { useEffect, useState } from 'react';
import {
    MDBCard,
    MDBCardImage,
    MDBCardBody,
    MDBCardTitle,
    MDBCardText,
    MDBRow,
    MDBCol,
    MDBContainer
} from 'mdb-react-ui-kit';


export default function BikeShop() {
    const [bikes, setBikes] = useState([]);

    async function getBikes() {
        debugger;
        const response = await fetch('http://localhost:8080/bike-shop');
        const data = await response.json();

        setBikes(data);
    }

    useEffect(() => {
        getBikes();
    }, [])

    return (<>
        <MDBContainer>
            {bikes.map(element => (
                <MDBRow key={element.id} className='row-cols-1 row-cols-md-3 g-4'>
                    <MDBCol>
                        <MDBCard>
                            <MDBCardImage
                                src="https://mdbcdn.b-cdn.net/img/new/slides/041.webp"
                                alt='...'
                                position='top'
                            />
                            <MDBCardBody>
                                <MDBCardTitle>{element.fullModelName}</MDBCardTitle>
                                <MDBCardText>
                                    Type: {element.type}
                                </MDBCardText>
                                <MDBCardText>
                                    Drivetrain: {element.drive}
                                </MDBCardText>
                                <MDBCardText>
                                    Price: {element.price} ,-
                                </MDBCardText>
                            </MDBCardBody>
                        </MDBCard>
                    </MDBCol>
                </MDBRow>
            ))}
        </MDBContainer>
    </>);
}