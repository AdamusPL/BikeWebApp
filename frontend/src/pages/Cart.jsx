import { MDBCard, MDBListGroup, MDBListGroupItem, MDBTypography, MDBBtn, MDBContainer, MDBInput, MDBIcon,
    MDBModal, MDBModalDialog, MDBModalContent, MDBModalHeader, MDBModalTitle, MDBModalBody, MDBModalFooter
 } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";

import '../css/Cart.css'
import { useNavigate } from "react-router-dom";

export default function Cart() {

    const [products, setProducts] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [summaryPrice, setSummaryPrice] = useState(0.0);
    const [basicModal, setBasicModal] = useState(false);

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
        if (localStorage.getItem("token") === null) {
            navigate('/sign-in');
        }
        else{
            setBasicModal(!basicModal);
        }   
    }

    function toggleOpen(){
        setBasicModal(!basicModal);
    }

    function minusBike(element) {

    }

    function submitOrder(){
        const order = {
            clientId: 1,
            bikes: JSON.parse(localStorage.getItem('cart')).bikes,
            parts: JSON.parse(localStorage.getItem('cart')).parts
        };

        fetch(`http://localhost:8080/buy`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(order)

        }).then(response => {
            if (response.ok) {
                localStorage.removeItem('cart');
                navigate('/order-list')
        }})
    }

    return (<>
        <MDBContainer>
            <MDBTypography variant='h1 mt-2'>Cart</MDBTypography>
            <MDBCard>
                <MDBListGroup flush>
                    {!isLoading ?
                        products.bikes.map(element => (
                            <MDBListGroupItem key={element.id}>{element.fullModelName}
                                <MDBBtn onClick={() => removeBikeFromCart(element.id)} className="btn-close" color="none" aria-label="Close" />
                                <article className="number-of-items">
                                    <MDBIcon fas icon="minus" onClick={() => minusBike(element)} />
                                    <input className="form-control" style={{ width: '50px', marginLeft: '10px', marginRight: '10px' }} value={element.quantity}></input>
                                    <MDBIcon fas icon="plus" />
                                </article>
                                {element.price} ,-
                            </MDBListGroupItem>
                        ))
                        :
                        <p>Your cart is empty!</p>
                    }
                    {!isLoading ?
                        products.parts.map(element => (
                            <MDBListGroupItem key={element.id}>{element.fullModelName}
                                <MDBBtn onClick={() => removePartFromCart(element.id)} className="btn-close" color="none" aria-label="Close" />
                                <article className="number-of-items">
                                    <MDBIcon fas icon="minus" onClick={() => minusBike(element)} />
                                    <input className="form-control" style={{ width: '50px', marginLeft: '10px', marginRight: '10px' }} value={element.quantity}></input>
                                    <MDBIcon fas icon="plus" />
                                </article>
                                {element.price}
                            </MDBListGroupItem>
                        ))
                        :
                        <p>Your cart is empty!</p>
                    }
                    <MDBListGroupItem>
                        {!isLoading ?
                            summaryPrice : 0} ,-</MDBListGroupItem>
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