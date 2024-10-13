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
    MDBBtn,
    MDBCheckbox,
    MDBInput
} from 'mdb-react-ui-kit';
import { Link } from 'react-router-dom';


export default function BikeShop() {
    const [products, setProducts] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [filters, setFilters] = useState([]);
    const [keysArray, setKeysArray] = useState([]);

    async function getProducts() {
        const response = await fetch('http://localhost:8080/bike-shop');
        const data = await response.json();
        setProducts(data);
    }

    async function getFilters() {
        debugger;
        const response = await fetch('http://localhost:8080/get-bike-filters');
        const data = await response.json();

        const keysArray = Object.keys(data);
        setKeysArray(keysArray);
        setFilters(data);
        console.log(keysArray);
        console.log(data);
    }

    useEffect(() => {
        getProducts();
        getFilters();
        setIsLoading(false);
    }, [])

    return (<>
        <MDBContainer fluid>
            <MDBRow style={{ color: 'white' }} className="no-gutters h-100">
                <MDBCol md="auto" style={{ backgroundColor: '#1B5E20' }}>
                    {!isLoading ?
                        keysArray.map(element => (
                            <div key={element} className='mt-4'>
                                <p>{element}</p>
                                {filters[element].map(item => (
                                    <MDBCheckbox name='flexCheck' value='' id='flexCheckDefault' label={item} />
                                ))}
                            </div>
                        ))
                    :
                    <p>No filters available</p>}
                
                    <p className='mt-4'>Price</p>
                    <a><input className='form-control w-50'></input></a>
                    <a>-</a>
                    <a><input className='form-control w-50'></input></a>
                </MDBCol>

                <MDBCol md="9">
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
                </MDBCol>
            </MDBRow>
        </MDBContainer>
    </>);
}