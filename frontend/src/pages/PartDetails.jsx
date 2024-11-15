import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { MDBContainer, MDBRow, MDBCol, MDBBtn, MDBInput, MDBTextArea, MDBSpinner, MDBIcon } from "mdb-react-ui-kit";
import '../css/BikeDetails.css'
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";

export default function PartDetails() {

    const urlParameters = useParams();
    const [chosenProduct, setChosenProduct] = useState({});
    const [isLoading, setIsLoading] = useState(true);
    const [cart, setCart] = useState(JSON.parse(localStorage.getItem('cart')));
    const [isAvailable, setIsAvailable] = useState(true);

    const [numberOfStars, setNumberOfStars] = useState(0);
    const [opinion, setOpinion] = useState("");

    const [isReviewPosted, setIsReviewPosted] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        getDetailedInfo();
    }, []);

    useEffect(() => {
        checkAvailability();
    }, [chosenProduct, cart]);

    function checkAvailability() {
        if (chosenProduct.quantityInStock === 0) {
            setIsAvailable(false);
        }
        else {
            if (cart) {
                const part = cart.parts.find(b => b.id === chosenProduct.id);
                if (part) {
                    if (part.quantity >= chosenProduct.quantityInStock || part.quantity === 0) {
                        setIsAvailable(false);
                    }

                }
            }
        }
    }

    async function getDetailedInfo() {
        const response = await fetch(`http://localhost:8080/get-detailed-info-about-part?partId=${urlParameters.id}`);
        const data = await response.json();

        setChosenProduct(data);
        setIsLoading(false);
    }

    function addToCart() {
        if (localStorage.getItem('cart') === null) {
            localStorage.setItem('cart', JSON.stringify({ bikes: [], parts: [{ id: chosenProduct.id, quantity: 1 }] }));
        }
        else {
            let cart = JSON.parse(localStorage.getItem('cart'));
            const index = cart.parts.findIndex(b => b.id === chosenProduct.id);

            if (index !== -1) {
                cart.parts[index].quantity += 1;
                setCart(cart);
            }

            else {
                cart = {
                    ...cart,
                    parts: [...cart.parts, { id: chosenProduct.id, quantity: 1 }]
                };
            }

            localStorage.setItem('cart', JSON.stringify(cart));
        }

    }

    async function addReview() {
        const review = {
            numberOfStars: numberOfStars,
            description: opinion,
            clientId: 1,
            partId: chosenProduct.id
        };

        fetch(`http://localhost:8080/post-part-review`, {
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
        navigate('/part-shop');
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
                    <p>{chosenProduct.type}: {chosenProduct.attribute}</p>
                    <article id="buy-block">
                        <p>{chosenProduct.price} ,-</p>
                        {
                            isAvailable ?
                                <article>
                                    <MDBBtn className="ms-3" color="success" onClick={addToCart}>Add to cart</MDBBtn>
                                    <p className="mt-2">Quantity in stock: {chosenProduct.quantityInStock}</p>
                                </article>
                                :
                                <article>
                                    <MDBBtn className='me-1' color='secondary'>
                                        Add to cart
                                    </MDBBtn>
                                    <p>It's not available anymore!</p>
                                    <p className="mt-2">Quantity in stock: {chosenProduct.quantityInStock}</p>
                                </article>
                        }
                    </article>
                </MDBCol>
            </MDBRow>
            <p>{chosenProduct.description}</p>

            <p className="fw-light">Reviews</p>
            <p className="fw-lighter">Write a review</p>
            <div className="d-flex align-items-center mb-2">
                <a><input className="form-control" style={{ width: '50px' }} label="1-5" min="1" max="5" maxLength="1" onChange={(e) => { setNumberOfStars(e.target.value) }}></input></a>
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
                        <p className="mt-2">Nobody has reviewed this part. Be the first to write a review!</p>
                        :
                        chosenProduct.reviews.map(element => {
                            return (
                                <div key={element.id} className="mt-5">
                                    <p>{element.firstName} {element.lastName}</p>
                                    <article>{(() => {
                                        const options = [];

                                        for (let i = 0; i < element.numberOfStars; i++) {
                                            options.push(<FontAwesomeIcon icon={faStar} />)
                                        }

                                        return options;
                                    })()}</article>
                                    <p>{element.description}</p>
                                </div>
                            )
                        })
                    :
                    <article>
                        <MDBSpinner role='status'>
                            <span className='visually-hidden'>Loading...</span>
                        </MDBSpinner>
                    </article>
            }

        </MDBContainer>
    </>)
}