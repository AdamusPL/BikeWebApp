import {
    MDBCard, MDBListGroup, MDBListGroupItem, MDBTypography, MDBBtn, MDBContainer, MDBIcon,
    MDBModal, MDBModalDialog, MDBModalContent, MDBModalHeader, MDBModalTitle, MDBModalBody, MDBModalFooter,
    MDBCol
} from "mdb-react-ui-kit";
import { useEffect, useState } from "react";

import '../css/Cart.css'
import { useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";
import { useRole } from "../components/RoleProvider";

export default function Cart() {

    const [products, setProducts] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [summaryPrice, setSummaryPrice] = useState(0.0);
    const [basicModal, setBasicModal] = useState(false);
    const [cookies] = useCookies(['token']);
    const { role } = useRole();

    const navigate = useNavigate();

    useEffect(() => {
        fetchProducts();
    }, [role]);

    useEffect(() => {
        if (products) {
            calculateSum();
            setIsLoading(false);
        }
    }, [products, role]);

    async function fetchProducts() {
        if (role === 'ROLE_ADMIN') {
            navigate('/unauthorized');
        }

        if (!localStorage.getItem('cart')) {
            return;
        }

        const products = {
            bikes: JSON.parse(localStorage.getItem('cart')).bikes,
            parts: JSON.parse(localStorage.getItem('cart')).parts
        }

        if (products.bikes.length === 0 && products.parts.length === 0) {
            return;
        }

        fetch(`http://localhost:8080/get-cart-products`, {
            credentials: 'include',
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(products)

        }).then(response => {
            if (response.ok) {
                return response.json();
            }
        }).then(data => {
            setProducts(data);
        })
    }

    function calculateSum() {
        let priceToPay = 0.0;

        products.bikes.map(item => {
            priceToPay += item.price * item.quantity;
        })

        products.parts.map(item => {
            priceToPay += item.price * item.quantity;
        })

        setSummaryPrice(priceToPay);
    }

    function removeBikeFromCart(id) {
        let cart = JSON.parse(localStorage.getItem('cart'));

        cart.bikes = cart.bikes.filter(bike => bike.id !== id);

        let productsCopy = {
            ...products,
            bikes: products.bikes.filter((b) => b.id !== id)
        };

        localStorage.setItem('cart', JSON.stringify(cart));
        setProducts(productsCopy);
    }

    function removePartFromCart(id) {
        let cart = JSON.parse(localStorage.getItem('cart'));

        cart.parts = cart.parts.filter(part => part.id !== id);

        let productsCopy = {
            ...products,
            parts: products.parts.filter((part) => part.id !== id)
        };

        localStorage.setItem('cart', JSON.stringify(cart));
        setProducts(productsCopy);
    }

    function handleClick() {
        if (isLoading) {
            return;
        }

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

    function minusPart(id) {
        let cart = JSON.parse(localStorage.getItem('cart'));

        const part = cart.parts.find(b => b.id === id);

        if (part.quantity > 1) {
            part.quantity -= 1;
        }
        else {
            return;
        }

        let productsCopy = {
            ...products,
            parts: products.parts.map((part) => (
                part.id === id ? { ...part, quantity: part.quantity - 1 } : part
            ))
        };

        localStorage.setItem('cart', JSON.stringify(cart));

        setProducts(productsCopy);
    }

    function minusBike(id) {
        let cart = JSON.parse(localStorage.getItem('cart'));

        const bike = cart.bikes.find(b => b.id === id);

        if (bike.quantity > 1) {
            bike.quantity -= 1;
        }
        else {
            return;
        }

        let productsCopy = {
            ...products,
            bikes: products.bikes.map((b) => (
                b.id === id ? { ...b, quantity: b.quantity - 1 } : b
            ))
        };

        localStorage.setItem('cart', JSON.stringify(cart));

        setProducts(productsCopy);
    }

    function plusPart(id) {
        let cart = JSON.parse(localStorage.getItem('cart'));

        const part = cart.parts.find(b => b.id === id);

        const partProduct = products.parts.find(b => b.id === id);

        if (part.quantity < partProduct.quantityInStock) {
            part.quantity += 1;
        }
        else {
            return;
        }

        let productsCopy = {
            ...products,
            parts: products.parts.map((part) => (
                part.id === id ? { ...part, quantity: part.quantity + 1 } : part
            ))
        };

        localStorage.setItem('cart', JSON.stringify(cart));

        setProducts(productsCopy);
    }

    function plusBike(id) {
        let cart = JSON.parse(localStorage.getItem('cart'));

        const bike = cart.bikes.find(b => b.id === id);

        const partProduct = products.bikes.find(b => b.id === id);

        if (bike.quantity < partProduct.quantityInStock) {
            bike.quantity += 1;
        }
        else {
            return;
        }

        let productsCopy = {
            ...products,
            bikes: products.bikes.map((b) => (
                b.id === id ? { ...b, quantity: b.quantity + 1 } : b
            ))
        };

        localStorage.setItem('cart', JSON.stringify(cart));

        setProducts(productsCopy);
    }

    function submitOrder() {
        const order = {
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
            <MDBCard className="mt-4">
                <MDBListGroup>
                    <MDBListGroupItem>
                        <MDBTypography tag='h1'>Cart</MDBTypography>
                    </MDBListGroupItem>
                    {!isLoading ?
                        products.bikes.map(element => (
                            <MDBListGroupItem key={element.id}>
                                <article className='item'>
                                    <MDBCol>
                                        <span>{element.fullModelName}</span>
                                    </MDBCol>
                                    <MDBCol className='middle'>
                                        <MDBIcon className='minus' fas icon="minus" onClick={() => minusBike(element.id)} />
                                        <input className="form-control" style={{ width: '50px', marginLeft: '10px', marginRight: '10px' }} value={element.quantity} />
                                        <MDBIcon className='plus' fas icon="plus" onClick={() => plusBike(element.id)} />
                                    </MDBCol>
                                    <MDBCol className='middle'>
                                        <span className='price'>{element.quantity} x {element.price} zł</span>
                                    </MDBCol>
                                    <article className='close-button-cart'><MDBBtn onClick={() => removeBikeFromCart(element.id)} className="btn-close" color="none" aria-label="Close" /></article>
                                </article>
                            </MDBListGroupItem>
                        ))
                        :
                        null
                    }

                    {!isLoading ?
                        products.parts.map(element => (
                            <MDBListGroupItem key={element.id}>
                                <article className='item'>
                                    <MDBCol>
                                        <span>{element.fullModelName}</span>
                                    </MDBCol>
                                    <MDBCol className='middle'>
                                        <MDBIcon className='minus' fas icon="minus" onClick={() => minusPart(element.id)} />
                                        <input className="form-control" style={{ width: '50px', marginLeft: '10px', marginRight: '10px' }} value={element.quantity} />
                                        <MDBIcon className='plus' fas icon="plus" onClick={() => plusPart(element.id)} />
                                    </MDBCol>
                                    <MDBCol className='middle'>
                                        <span className='price'>{element.quantity} x {element.price} zł</span>
                                    </MDBCol>
                                    <article className='close-button-cart'><MDBBtn onClick={() => removePartFromCart(element.id)} className="btn-close" color="none" aria-label="Close" /></article>
                                </article>
                            </MDBListGroupItem>
                        ))
                        :
                        null
                    }

                    {isLoading ?
                        <MDBListGroupItem>
                            Your cart is empty
                        </MDBListGroupItem>
                        : <MDBListGroupItem></MDBListGroupItem>
                    }

                    <MDBListGroupItem>
                        <MDBTypography tag='h4'>Summary</MDBTypography>
                    </MDBListGroupItem>
                    <MDBListGroupItem>
                        <MDBTypography tag='strong' className="button-out">{!isLoading ?
                            summaryPrice : 0} zł</MDBTypography>
                    </MDBListGroupItem>
                </MDBListGroup>
            </MDBCard>
            <MDBBtn className="mt-4 classic-button" onClick={handleClick}>Buy now</MDBBtn>
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
                            <MDBBtn onClick={submitOrder} className="classic-button">Yes</MDBBtn>
                        </MDBModalFooter>
                    </MDBModalContent>
                </MDBModalDialog>
            </MDBModal>
        </MDBContainer >
    </>)
}