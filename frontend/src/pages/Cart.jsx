import {
    MDBCard, MDBListGroup, MDBListGroupItem, MDBTypography, MDBBtn, MDBContainer, MDBInput, MDBIcon,
    MDBModal, MDBModalDialog, MDBModalContent, MDBModalHeader, MDBModalTitle, MDBModalBody, MDBModalFooter
} from "mdb-react-ui-kit";
import { useEffect, useState } from "react";

import '../css/Cart.css'
import { useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";

export default function Cart() {

    const [products, setProducts] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [summaryPrice, setSummaryPrice] = useState(0.0);
    const [basicModal, setBasicModal] = useState(false);
    const [cookies, setCookie] = useCookies(['token']);

    const navigate = useNavigate();

    useEffect(() => {
        fetchProducts();
    }, []);

    useEffect(() => {
        if (products) {
            calculateSum();
            setIsLoading(false);
        }
    }, [products]);

    async function fetchProducts() {
        const products = {
            bikes: JSON.parse(localStorage.getItem('cart')).bikes,
            parts: JSON.parse(localStorage.getItem('cart')).parts
        }

        fetch(`http://localhost:8080/get-cart-products`, {
            credentials: 'include',
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(products)

        }).then(response => {
            debugger;
            if (response.ok) {
                return response.json();
            }
        }).then(data => {
            debugger;
            setProducts(data);
        })
    }

    function calculateSum() {
        let priceToPay = 0.0;

        products.bikes.map(item => {
            priceToPay += item.price;
        })

        products.parts.map(item => {
            priceToPay += item.price;
        })

        setSummaryPrice(priceToPay);
    }

    function removeBikeFromCart(id) {
        let cart = JSON.parse(localStorage.getItem('cart'));

        cart.bikes = cart.bikes.filter(bike => bike.id !== id);

        localStorage.setItem('cart', JSON.stringify(cart));
    }

    function removePartFromCart(id) {
        let cart = JSON.parse(localStorage.getItem('cart'));

        cart.parts = cart.parts.filter(part => part.id !== id);

        localStorage.setItem('cart', JSON.stringify(cart));
    }

    function handleClick() {
        if (cookies.token === undefined) {
            navigate('/sign-in');
        }
        else {
            setBasicModal(!basicModal);
        }
    }

    function toggleOpen() {
        setBasicModal(!basicModal);
    }

    function minusBike(id) {
        debugger;
        let cart = JSON.parse(localStorage.getItem('cart'));

        const bike = cart.bikes.find(b => b.id === id);

        if (bike.quantity > 1) {
            bike.quantity -= 1;
        }

        localStorage.setItem('cart', JSON.stringify(cart));

        let productsCopy = products;

        const bikeProduct = productsCopy.bikes.find(b => b.id === id);

        if (bikeProduct.quantity > 1) {
            bikeProduct.quantity -= 1;
        }

        setProducts(productsCopy);
    }

    function plusBike(id) {
        debugger;
        let cart = JSON.parse(localStorage.getItem('cart'));

        const bike = cart.bikes.find(b => b.id === id);

        let productsCopy = products;
        const bikeProduct = productsCopy.bikes.find(b => b.id === id);

        if (bike.quantity < bikeProduct.quantityInStock) {
            bike.quantity += 1;
        }

        localStorage.setItem('cart', JSON.stringify(cart));

        if (bikeProduct.quantity < bike.quantityInStock) {
            bikeProduct.quantity += 1;
        }

        setProducts(productsCopy);
    }

    function minusPart(id) {
        debugger;
        let cart = JSON.parse(localStorage.getItem('cart'));

        const part = cart.parts.find(b => b.id === id);

        let productsCopy = products;
        const partProduct = productsCopy.parts.find(b => b.id === id);

        if (part.quantity > 1) {
            part.quantity -= 1;
        }

        localStorage.setItem('cart', JSON.stringify(cart));

        if (partProduct.quantity > 1) {
            partProduct.quantity -= 1;
        }

        setProducts(productsCopy);
    }

    function plusPart(id) {
        debugger;
        let cart = JSON.parse(localStorage.getItem('cart'));

        const part = cart.parts.find(b => b.id === id);

        let productsCopy = products;
        const partProduct = productsCopy.parts.find(b => b.id === id);

        if (part.quantity < partProduct.quantityInStock) {
            part.quantity += 1;
        }

        localStorage.setItem('cart', JSON.stringify(cart));

        if (partProduct.quantity < part.quantityInStock) {
            partProduct.quantity += 1;
        }

        setProducts(productsCopy);
    }

    function submitOrder() {
        const order = {
            clientId: 1,
            bikes: JSON.parse(localStorage.getItem('cart')).bikes,
            parts: JSON.parse(localStorage.getItem('cart')).parts
        };

        fetch(`http://localhost:8080/buy`, {
            credentials: 'include',
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(order)

        }).then(response => {
            if (response.ok) {
                localStorage.removeItem('cart');
                navigate('/order-list')
            }
        })
    }

    return (<>
        <MDBContainer>
            <MDBTypography variant='h1 mt-2'>Cart</MDBTypography>
            <MDBCard>
                <MDBListGroup>
                    <MDBListGroupItem>
                        <MDBTypography tag='h4'>Bikes</MDBTypography>
                    </MDBListGroupItem>
                    {!isLoading ?
                        products.bikes.map(element => (
                            <MDBListGroupItem key={element.id}>{element.fullModelName}
                                <MDBBtn onClick={() => removeBikeFromCart(element.id)} className="btn-close" color="none" aria-label="Close" />
                                <article className="number-of-items">
                                    <MDBIcon fas icon="minus" onClick={() => minusBike(element.id)} />
                                    <input className="form-control" style={{ width: '50px', marginLeft: '10px', marginRight: '10px' }} defaultValue={element.quantity} />
                                    <MDBIcon fas icon="plus" onClick={() => plusBike(element.id)} />
                                </article>
                                {element.price} ,-
                            </MDBListGroupItem>
                        ))
                        :
                        <p>Your cart is empty!</p>
                    }
                    <MDBListGroupItem>
                        <MDBTypography tag='h4'>Parts</MDBTypography>
                    </MDBListGroupItem>
                    {!isLoading ?
                        products.parts.map(element => (
                            <MDBListGroupItem key={element.id}>{element.fullModelName}
                                <MDBBtn onClick={() => removePartFromCart(element.id)} className="btn-close" color="none" aria-label="Close" />
                                <article className="number-of-items">
                                    <MDBIcon fas icon="minus" onClick={() => minusPart(element.id)} />
                                    <input className="form-control" style={{ width: '50px', marginLeft: '10px', marginRight: '10px' }} defaultValue={element.quantity} />
                                    <MDBIcon fas icon="plus" onClick={() => plusPart(element.id)} />
                                </article>
                                {element.price} ,-
                            </MDBListGroupItem>
                        ))
                        :
                        <p>Your cart is empty!</p>
                    }
                    <MDBListGroupItem>
                        <MDBTypography tag='h4'>Summary</MDBTypography>
                    </MDBListGroupItem>
                    <MDBListGroupItem>
                        <MDBTypography tag='dt' sm='3' color="success">{!isLoading ?
                            summaryPrice : 0} ,-</MDBTypography>
                    </MDBListGroupItem>
                </MDBListGroup>
            </MDBCard>
            <MDBBtn className="mt-4" color="success" onClick={handleClick}>Buy now</MDBBtn>
            <MDBModal open={basicModal} onClose={() => setBasicModal(false)} tabIndex='-1'>
                <MDBModalDialog>
                    <MDBModalContent>
                        <MDBModalHeader>
                            <MDBModalTitle>Are you sure you want to buy?</MDBModalTitle>
                            <MDBBtn className='btn-close' color='none' onClick={toggleOpen}></MDBBtn>
                        </MDBModalHeader>
                        <MDBModalBody>By clicking "Yes" button, you accept the store policy and order with obligation to pay on-site.</MDBModalBody>

                        <MDBModalFooter>
                            <MDBBtn color='secondary' onClick={toggleOpen}>
                                Give me more time
                            </MDBBtn>
                            <MDBBtn onClick={submitOrder} color="success">Yes</MDBBtn>
                        </MDBModalFooter>
                    </MDBModalContent>
                </MDBModalDialog>
            </MDBModal>
        </MDBContainer >
    </>)
}