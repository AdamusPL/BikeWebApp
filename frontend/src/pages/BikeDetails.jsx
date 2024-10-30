import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { MDBContainer, MDBRow, MDBCol, MDBBtn, MDBInput, MDBTextArea, MDBSpinner, MDBIcon } from "mdb-react-ui-kit";
import '../css/BikeDetails.css'
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";

export default function BikeDetails() {

    const urlParameters = useParams();
    const [chosenProduct, setChosenProduct] = useState({});
    const [keysArray, setKeysArray] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [isAvailable, setIsAvailable] = useState(true);

    const [numberOfStars, setNumberOfStars] = useState(0);
    const [opinion, setOpinion] = useState("");
    const [cart, setCart] = useState(JSON.parse(localStorage.getItem('cart')));

    const [isReviewPosted, setIsReviewPosted] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        getDetailedInfo();
    }, []);

    useEffect(() => {
        checkAvailability();
    }, [chosenProduct, cart]);

    function checkAvailability() {
        if (cart) {
            const bike = cart.bikes.find(b => b.id === chosenProduct.id);
            if (bike) {
                if (bike.quantity === chosenProduct.quantityInStock) {
                    setIsAvailable(false);
                }
                
            }
        }
    }

    function addToCart() {
        if (localStorage.getItem('cart') === null) {
            localStorage.setItem('cart', JSON.stringify({ bikes: [{ id: chosenProduct.id, quantity: 1 }], parts: [] }));
        }
        else {
            var cart = JSON.parse(localStorage.getItem('cart'));
            const index = cart.bikes.findIndex(b => b.id === chosenProduct.id);

            cart.bikes[index].quantity += 1;

            localStorage.setItem('cart', JSON.stringify(cart));
            setCart(cart);
        }

    }

    async function getDetailedInfo() {
        const response = await fetch(`http://localhost:8080/get-detailed-info-about-bike?bikeId=${urlParameters.id}`);
        const data = await response.json();

        const keysArray = Object.keys(data.parts);
        setKeysArray(keysArray);
        setChosenProduct(data);
        setIsLoading(false);
        console.log(data.reviews);
    }

    async function addReview() {
        const review = {
            numberOfStars: numberOfStars,
            description: opinion,
            clientId: 1,
            bikeId: chosenProduct.id
        };

        fetch(`http://localhost:8080/post-bike-review`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(review)

        }).then(response => {
            if (response.ok) {
                setIsReviewPosted(true);
            }
        })
    }

    function backToShop() {
        navigate('/bike-shop');
    }

    return (<>
        <MDBContainer>
            <MDBIcon fas icon="arrow-left" className="mt-2" onClick={backToShop} />
            <MDBRow className="mt-2">
                <MDBCol md="8">
                    <figure className='bg-image' style={{ position: 'relative', display: 'inline-block' }}>
                        <img
                            src="https://mdbcdn.b-cdn.net/img/new/slides/041.webp"
                            className='img-fluid rounded shadow-3'
                            alt='...'
                        />
                    </figure>
                    <h1>{chosenProduct.fullModelName}</h1>
                </MDBCol>
                <MDBCol md="4">
                    {
                        keysArray.map(element => (
                            <p key={element}>{element}: {chosenProduct.parts[element]}</p>
                        ))
                    }
                    <a>{chosenProduct.price} ,-</a>
                    {
                        isAvailable ? <MDBBtn className="ms-3" color="success" onClick={addToCart}>Add to cart</MDBBtn>
                            :
                            <div>
                                <MDBBtn className='me-1' color='secondary'>
                                    Add to cart
                                </MDBBtn>
                                <p>It's not available anymore!</p>
                            </div>
                    }
                    <p className="mt-2">Quantity in stock: {chosenProduct.quantityInStock}</p>
                </MDBCol>
            </MDBRow>
            <p>{chosenProduct.description}</p>

            <p className="fw-light">Reviews</p>
            <p className="fw-lighter">Write a review</p>
            <div className="d-flex align-items-center mb-2">
                <a><input className="form-control" style={{ width: '50px' }} label="1-5" min="1" max="5" maxlength="1" onChange={(e) => { setNumberOfStars(e.target.value) }}></input></a>
                <a>/5</a>
            </div>
            <MDBTextArea label="Opinion" id="textAreaExample" rows="{4}" onChange={(e) => { setOpinion(e.target.value) }} />
            <MDBBtn className="mt-2" color="success" onClick={addReview}>Add review</MDBBtn>

            {
                isReviewPosted ?
                    <p>Review posted successfully</p>
                    :
                    <p></p>
            }

            {
                !isLoading ?
                    chosenProduct.reviews.length === 0 ?
                        <p className="mt-2">Nobody has reviewed this bike. Be the first to write a review!</p>
                        :
                        chosenProduct.reviews.map(element => {
                            return (
                                <div className="mt-5">
                                    <p>{element.firstName} {element.lastName}</p>
                                    <p>{(() => {
                                        const options = [];

                                        for (var i = 0; i < element.numberOfStars; i++) {
                                            options.push(<FontAwesomeIcon icon={faStar} />)
                                        }

                                        return options;
                                    })()}</p>
                                    <p>{element.description}</p>
                                </div>
                            )
                        })
                    :
                    <p>
                        <MDBSpinner role='status'>
                            <span className='visually-hidden'>Loading...</span>
                        </MDBSpinner>
                    </p>
            }

        </MDBContainer >
    </>)
}