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
    MDBRipple
} from 'mdb-react-ui-kit';
import { Link } from 'react-router-dom';

import '../css/BikeShop.css'
import Dialog from '../components/Dialog';

export default function BikeShop() {
    const [isDialogOpen, setIsDialogOpen] = useState(false);

    const [products, setProducts] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [filters, setFilters] = useState([]);
    const [keysArray, setKeysArray] = useState([]);
    const [cart, setCart] = useState(JSON.parse(localStorage.getItem('cart')));

    useEffect(() => {
        getProducts();
        getFilters();
        setIsLoading(false);
    }, []);

    async function getProducts() {
        const response = await fetch('http://localhost:8080/bike-shop');
        const data = await response.json();
        // const updatedData = data.map(item => (
        //     return {...item,
        //     isAvailable: };
        // ));
        setProducts(data);
    }

    async function getFilters() {
        const response = await fetch('http://localhost:8080/get-bike-shop-filters');
        const data = await response.json();

        const keysArray = Object.keys(data);
        setKeysArray(keysArray);
        setFilters(data);
    }

    function addToCart(id) {
        if (localStorage.getItem('cart') === null) {
            localStorage.setItem('cart', JSON.stringify({ bikes: [{ id: id, quantity: 1 }], parts: [] }));
        }
        else {
            let cart = JSON.parse(localStorage.getItem('cart'));
            const index = cart.bikes.findIndex(b => b.id === id);

            if (index !== -1) {
                cart.bikes[index].quantity += 1;
                setCart(cart);
            }

            else {
                cart = {
                    ...cart,
                    bikes: [...cart.bikes, { id: id, quantity: 1 }]
                };
            }

            localStorage.setItem('cart', JSON.stringify(cart));
        }
        setIsDialogOpen(!isDialogOpen);
    }

    function closeDialog(){
        setIsDialogOpen(!isDialogOpen);
    }

    return (<>
        <MDBContainer fluid>
            <MDBRow className="h-100">
                <MDBCol id='sidebar' md="auto">
                    {!isLoading ?
                        keysArray.map(element => (
                            <article key={element} className='mt-4'>
                                <p>{element}</p>
                                {filters[element].map(item => (
                                    <MDBCheckbox key={item} name='flexCheck' value='' id='flexCheckDefault' label={item} />
                                ))}
                            </article>
                        ))
                        :
                        <p>No filters available</p>
                    }

                    <p className='mt-4'>Price</p>
                    <article id='price'>
                        <input className='form-control input'></input>
                        <p id='minus'>-</p>
                        <input className='form-control input'></input>
                    </article>
                </MDBCol>

                <MDBCol>
                    <article id="button">
                        <MDBBtn className="mt-4" color="success" href='/add-bike'>Add new bike</MDBBtn>
                    </article>

                    <MDBCol md="9">
                        <MDBRow className='row-cols-1 row-cols-md-3 g-4 mt-2'>
                            {!isLoading ?
                                products.length === 0 ?
                                    <p>Currently, we are out of stock</p>
                                    :
                                    products.map(element => (
                                        <MDBCol key={element.id}>
                                            <MDBCard>
                                                <Link to={`/bike-shop/${element.id}`}>
                                                    <MDBRipple rippleColor='light' rippleTag='div' className='bg-image hover-overlay'>
                                                        <MDBCardImage
                                                            src="https://mdbcdn.b-cdn.net/img/new/slides/041.webp"
                                                            alt='...'
                                                            position='top'
                                                        />
                                                    </MDBRipple>
                                                </Link>
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
                                                    <Dialog isOpen={isDialogOpen} toggleOpen={() => addToCart(element.id)} toggleClose={closeDialog} />
                                                </MDBCardBody>
                                            </MDBCard>
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
                </MDBCol >
            </MDBRow >
        </MDBContainer>
    </>);
}