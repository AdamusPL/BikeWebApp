import { MDBCard, MDBListGroup, MDBListGroupItem, MDBTypography, MDBBtn, MDBContainer, MDBInput } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";

export default function Cart() {

    const [products, setProducts] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [summaryPrice, setSummaryPrice] = useState(0.0);

    async function fetchProducts() {
        const products = {
            bikeIds: JSON.parse(localStorage.getItem('cart')).bikes,
            partIds: JSON.parse(localStorage.getItem('cart')).parts
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
            console.log(data);
        })
    }

    function calculateSum() {
        debugger;
        var priceToPay = 0.0;

        products.map(item => {
            priceToPay += item.price
        })

        setSummaryPrice(priceToPay);
    }

    useEffect(() => {
        fetchProducts();
        calculateSum();
        setIsLoading(false);
    }, []);

    return (<>
        <MDBContainer>
            <MDBTypography variant='h1 mt-2'>Cart</MDBTypography>
            <MDBCard>
                <MDBListGroup flush>
                    {!isLoading ?
                        products.map(element => (
                            <MDBListGroupItem key={element.id}>{element.fullModelName}
                                <MDBBtn className="btn-close" color="none" aria-label="Close" />
                                <MDBInput value={element.quantity}></MDBInput>
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
        </MDBContainer>
    </>)
}