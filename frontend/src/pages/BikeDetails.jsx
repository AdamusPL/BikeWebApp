import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { MDBContainer, MDBRow, MDBCol, MDBBtn, MDBInput, MDBTextArea, MDBSpinner, MDBIcon } from "mdb-react-ui-kit";
import '../css/BikeDetails.css'
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import Dialog from "../components/Dialog";

export default function BikeDetails() {
    const [isDialogOpen, setIsDialogOpen] = useState(false);

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

    function closeDialog() {
        setIsDialogOpen(!isDialogOpen);
    }

    function checkAvailability() {
        if (chosenProduct.quantityInStock === 0) {
            setIsAvailable(false);
        }
        else {
            if (cart) {
                const bike = cart.bikes.find(b => b.id === chosenProduct.id);
                if (bike) {
                    if (bike.quantity >= chosenProduct.quantityInStock || bike.quantity === 0) {
                        setIsAvailable(false);
                    }

                }
            }
        }
    }

    function addToCart() {
        if (localStorage.getItem('cart') === null) {
            localStorage.setItem('cart', JSON.stringify({ bikes: [{ id: chosenProduct.id, quantity: 1 }], parts: [] }));
        }
        else {
            let cart = JSON.parse(localStorage.getItem('cart'));
            const index = cart.bikes.findIndex(b => b.id === chosenProduct.id);

            if (index !== -1) {
                cart.bikes[index].quantity += 1;
                setCart(cart);
            }

            else {
                cart = {
                    ...cart,
                    bikes: [...cart.bikes, { id: chosenProduct.id, quantity: 1 }]
                };
            }

            localStorage.setItem('cart', JSON.stringify(cart));
        }

        setIsDialogOpen(!isDialogOpen);

    }

    async function getDetailedInfo() {
        const response = await fetch(`http://localhost:8080/get-detailed-info-about-bike?bikeId=${urlParameters.id}`);
        const data = await response.json();

        debugger;
        const keysArray = Object.keys(data.parts);
        setKeysArray(keysArray);
        setChosenProduct(data);
        setIsLoading(false);
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
                    <figure id="image-product" className='bg-image'>
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
                    <article id="buy-block">
                        <p>{chosenProduct.price} ,-</p>
                        {
                            isAvailable ?
                                <article>
                                    <Dialog isOpen={isDialogOpen} toggleOpen={() => addToCart(chosenProduct.id)} toggleClose={closeDialog} />
                                    <p className="mt-2">Quantity in stock: {chosenProduct.quantityInStock}</p>
                                </article>
                                :
                                <article>
                                    <MDBBtn className='me-1' color='secondary'>
                                        Add to cart
                                    </MDBBtn>
                                    <p className="mt-2">It's not available anymore!</p>
                                    <p className="mt-2">Quantity in stock: {chosenProduct.quantityInStock}</p>
                                </article>
                        }
                    </article>
                </MDBCol>
            </MDBRow>
            <p>{chosenProduct.description}</p>

            <p className="fw-light">Reviews</p>
            <p className="fw-lighter">Write a review</p>
            <article className="d-flex align-items-center mb-2">
                <input id="stars" className="form-control" label="1-5" min="1" max="5" maxLength="1" onChange={(e) => { setNumberOfStars(e.target.value) }}></input>
                /5
            </article>
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
                                <div key={element.id} className="mt-5">
                                    <p>{element.firstName} {element.lastName}</p>
                                    <p>{(() => {
                                        const options = [];

                                        for (let i = 0; i < element.numberOfStars; i++) {
                                            options.push(<FontAwesomeIcon icon={faStar} />)
                                        }

                                        return options;
                                    })()}</p>
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

        </MDBContainer >
    </>)
}