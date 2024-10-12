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
import { Link } from 'react-router-dom';


export default function BikeShop() {
    const [products, setProducts] = useState([]);

    async function getProducts() {
        const response = await fetch('http://localhost:8080/bike-shop');
        const data = await response.json();

        setProducts(data);
    }

    useEffect(() => {
        getProducts();
    }, [])

    return (<>
        <MDBContainer>
            {products.map(element => (
                <MDBRow key={element.id} className='row-cols-1 row-cols-md-3 g-4 mt-2'>
                    <MDBCol>
                        <Link to={`/bike-shop/${element.id}`}>
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
                        </Link>
                    </MDBCol>
                </MDBRow>
            ))}
        </MDBContainer>
    </>);
}