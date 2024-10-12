import React, { useEffect, useState } from 'react';
import {
    MDBCard,
    MDBCardImage,
    MDBCardBody,
    MDBCardTitle,
    MDBCardText,
    MDBRow,
    MDBCol,
    MDBContainer,
    MDBSpinner,
    MDBBtn
} from 'mdb-react-ui-kit';
import { Link } from 'react-router-dom';


export default function BikeShop() {
    const [products, setProducts] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    async function getProducts() {
        const response = await fetch('http://localhost:8080/bike-shop');
        const data = await response.json();

        setProducts(data);
        setIsLoading(false);
    }

    useEffect(() => {
        getProducts();
    }, [])

    return (<>
        <MDBContainer>
            <MDBRow className='row-cols-1 row-cols-md-3 g-4 mt-2'>
                {!isLoading ?
                    products.length === 0 ?
                        <p>Currently, we are out of stock</p>
                        :
                        products.map(element => (
                            <MDBCol key={element.id}>
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
                                            <MDBBtn color='success' href='#'>Add to cart</MDBBtn>
                                        </MDBCardBody>
                                    </MDBCard>
                                </Link>
                            </MDBCol>
                        ))
                    :
                    <p>
                        <MDBSpinner role='status'>
                            <span className='visually-hidden'>Loading...</span>
                        </MDBSpinner>
                    </p>
                }
            </MDBRow>
        </MDBContainer>
    </>);
}