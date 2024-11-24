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
    MDBRipple,
    MDBTypography
} from 'mdb-react-ui-kit';
import { Link } from 'react-router-dom';
import Dialog from '../components/Dialog';
import { useRole } from '../components/RoleProvider';
import '../css/PartShop.css'

export default function PartShop() {
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const [products, setProducts] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [keysArray, setKeysArray] = useState([]);
    const [filters, setFilters] = useState([]);
    const [cart, setCart] = useState(JSON.parse(localStorage.getItem('cart')));
    const { isShopAssistant } = useRole();

    useEffect(() => {
        getProducts();
        getFilters();
        setIsLoading(false);
    }, []);

    async function getProducts() {
        const response = await fetch('http://localhost:8080/part-shop');
        const data = await response.json();

        const cart = JSON.parse(localStorage.getItem('cart'));

        data.map(item => {
            item.isAvailable = true;
            if (item.quantityInStock === 0) {
                item.isAvailable = false;
            }
            else {
                if (cart) {
                    const found = cart.parts.find(cartItem => cartItem.id === item.id);
                    if (found) {
                        if (found.quantity >= item.quantityInStock || found.quantity === 0) {
                            item.isAvailable = false;
                        }
                    }
                }
            }
        })

        setProducts(data);
    }

    async function getFilters() {
        const response = await fetch('http://localhost:8080/get-part-filters');
        const data = await response.json();

        const keysArray = Object.keys(data);
        setKeysArray(keysArray);
        setFilters(data);
    }

    function addToCart(id) {
        if (localStorage.getItem('cart') === null) {
            localStorage.setItem('cart', JSON.stringify({ bikes: [], parts: [{ id: id, quantity: 1 }] }));
        }
        else {
            let cart = JSON.parse(localStorage.getItem('cart'));
            const index = cart.parts.findIndex(b => b.id === id);

            if (index !== -1) {
                cart.parts[index].quantity += 1;
                setCart(cart);
            }

            else {
                cart = {
                    ...cart,
                    parts: [...cart.parts, { id: id, quantity: 1 }]
                };
            }

            localStorage.setItem('cart', JSON.stringify(cart));
        }

        let cartAfterModifying = JSON.parse(localStorage.getItem('cart'));

        let productsCopy = products;
        const product = productsCopy.find(item => item.id === id);

        const index = cartAfterModifying.parts.findIndex(b => b.id === id);

        if (cartAfterModifying.parts[index].quantity >= product.quantityInStock) {
            product.isAvailable = false;
        }

        setProducts(productsCopy);

        setIsDialogOpen(!isDialogOpen);

    }

    function closeDialog() {
        setIsDialogOpen(!isDialogOpen);
    }

    function removeFromDb(id) {
        fetch(`http://localhost:8080/delete-part?partId=${id}`, {
            credentials: 'include',
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    setProducts((prevItems) => prevItems.filter((item) => item.id !== id));
                }
            });;
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
                    {isShopAssistant ?
                        <article id="button">
                            <MDBBtn className="mt-4" color="success" href='/add-part'>Add new part</MDBBtn>
                        </article>
                        : null
                    }

                    <MDBCol md="11">
                        <MDBRow className='row-cols-1 row-cols-md-3 g-4 mt-2'>
                            {!isLoading ?
                                products.length === 0 ?
                                    <p>Currently, we are out of stock</p>
                                    :
                                    products.map(element => (
                                        <MDBCol key={element.id}>
                                            {isShopAssistant ? <article className='close-button'><MDBBtn onClick={() => removeFromDb(element.id)} className="btn-close" color="none" aria-label="Close" /></article> : null}
                                            <MDBCard>
                                                <Link to={`/part-shop/${element.id}`}>
                                                    <MDBRipple rippleColor='light' rippleTag='div' className='bg-image hover-overlay'>
                                                        <MDBCardImage
                                                            src="https://mdbcdn.b-cdn.net/img/new/slides/041.webp"
                                                            alt='...'
                                                            position='top'
                                                        />
                                                    </MDBRipple>
                                                </Link>
                                                <MDBCardBody>
                                                    <MDBCardTitle tag='h2' className='mb-4'>{element.make} {element.modelName}</MDBCardTitle>
                                                    <MDBCardText className='choice'>
                                                        <MDBTypography className='margin-item' sm='3' tag='dt'>Type:</MDBTypography>
                                                        <MDBTypography className='value-item' sm='9' tag='dd'>{element.type}</MDBTypography>
                                                    </MDBCardText>
                                                    <MDBCardText className='choice'>
                                                        <MDBTypography className='margin-item' sm='3' tag='dt'>Kind:</MDBTypography>
                                                        <MDBTypography className='value-item' sm='9' tag='dd'>{element.attribute}</MDBTypography>
                                                    </MDBCardText>
                                                    <MDBCardText className='choice'>
                                                        <MDBTypography className='margin-item' sm='3' tag='dt'>Price:</MDBTypography>
                                                        <MDBTypography className='value-item' sm='9' tag='dd'>{element.price} zł</MDBTypography>
                                                    </MDBCardText>
                                                    <MDBCardText className='choice'>
                                                        <MDBTypography className='margin-item' sm='3' tag='dt'>Quantity in stock:</MDBTypography>
                                                        <MDBTypography className='value-item' sm='9' tag='dd'>{element.quantityInStock}</MDBTypography>
                                                    </MDBCardText>
                                                    {!isShopAssistant ?
                                                        element.isAvailable ?
                                                            <Dialog isOpen={isDialogOpen} toggleOpen={() => addToCart(element.id)} toggleClose={closeDialog} />
                                                            :
                                                            <article>
                                                                <MDBBtn className='me-1' color='secondary'>
                                                                    Add to cart
                                                                </MDBBtn>
                                                                <MDBTypography tag='dt' sm='3' className='mt-2'>It's not available anymore!</MDBTypography>
                                                            </article>
                                                        : null
                                                    }
                                                </MDBCardBody>
                                            </MDBCard>
                                        </MDBCol>
                                    ))
                                :
                                <article>
                                    <MDBSpinner role='status'>
                                        <span className='visually-hidden'>Loading...</span>
                                    </MDBSpinner>
                                </article>
                            }
                        </MDBRow>
                    </MDBCol>
                </MDBCol >
            </MDBRow >
        </MDBContainer>
    </>);
}