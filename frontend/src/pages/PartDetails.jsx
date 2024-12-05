import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { MDBContainer, MDBRow, MDBCol, MDBBtn, MDBTextArea, MDBSpinner, MDBIcon, MDBModal, MDBModalDialog, MDBModalContent, MDBModalHeader, MDBModalTitle, MDBModalBody, MDBModalFooter, MDBTypography } from "mdb-react-ui-kit";
import '../css/BikeDetails.css'
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import { useRole } from "../components/RoleProvider";
import Dialog from "../components/Dialog";

export default function PartDetails() {
    const [isDialogOpen, setIsDialogOpen] = useState(false);

    const urlParameters = useParams();
    const [chosenProduct, setChosenProduct] = useState({});
    const [isLoading, setIsLoading] = useState(true);
    const [cart, setCart] = useState(JSON.parse(localStorage.getItem('cart')));
    const [isAvailable, setIsAvailable] = useState(true);

    const [numberOfStars, setNumberOfStars] = useState(0);
    const [opinion, setOpinion] = useState("");

    const [reviewStatus, setIsReviewPosted] = useState("");
    const navigate = useNavigate();

    const [basicModal, setBasicModal] = useState(false);
    const toggleOpen = () => setBasicModal(!basicModal);

    const { role } = useRole();

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

        let cartAfterModifying = JSON.parse(localStorage.getItem('cart'));

        const index = cartAfterModifying.parts.findIndex(b => b.id === chosenProduct.id);

        if (cartAfterModifying.parts[index].quantity >= chosenProduct.quantityInStock) {
            setIsAvailable(false);
        }

        setIsDialogOpen(!isDialogOpen);

    }

    async function addReview() {
        const review = {
            numberOfStars: numberOfStars,
            description: opinion,
            partId: chosenProduct.id
        };

        fetch(`http://localhost:8080/post-part-review`, {
            credentials: 'include',
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(review)

        }).then(response => {
            if (response.ok) {
                setChosenProduct((chosenProduct) => ({
                    ...chosenProduct,
                    reviews: [...chosenProduct.reviews, review],
                }));
            }
            return response.text();
        }).then(data => {
            setIsReviewPosted(data);
        })
    }

    function backToShop() {
        navigate('/part-shop');
    }

    function removeFromDb(id) {
        fetch(`http://localhost:8080/delete-review?reviewId=${id}`, {
            credentials: 'include',
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    setChosenProduct((chosenProduct) => ({
                        ...chosenProduct,
                        reviews: chosenProduct.reviews.filter((r) => r.id !== id),
                    }));
                    toggleOpen();
                }
            });
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
                            width='700px'
                        />
                    </figure>
                    <h1>{chosenProduct.fullModelName}</h1>
                </MDBCol>
                <MDBCol md="4">
                    <MDBRow tag='dl'>
                        <MDBCol tag='dt'>
                            {chosenProduct.type}
                        </MDBCol>
                        <MDBCol tag='dd'>
                            {chosenProduct.attribute}
                        </MDBCol>
                    </MDBRow>
                    <article id="buy-block">
                        <p>{chosenProduct.price} zł</p>
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
            {role === 'ROLE_USER' ?
                <article>
                    <p>Write a review</p>
                    <div className="d-flex align-items-center mb-2">
                        <input id="stars" className="form-control" style={{ width: '50px' }} label="1-5" min="1" max="5" maxLength="1" onChange={(e) => { setNumberOfStars(e.target.value) }}></input>
                        <span>/5</span>
                    </div>
                    <MDBTextArea label="Opinion" id="textAreaExample" maxLength="500" rows="{4}" onChange={(e) => { setOpinion(e.target.value) }} />
                    <MDBBtn className="mt-2 classic-button mb-4" onClick={addReview}>Add review</MDBBtn>
                </article>
                : <p>You must be signed-in to post a review</p>
            }
            <div>
                <MDBTypography id="info" tag='strong'>{reviewStatus}</MDBTypography>
            </div>

            {
                !isLoading ?
                    chosenProduct.reviews.length === 0 ?
                        <p className="mt-2">Nobody has reviewed this part. Be the first to write a review!</p>
                        :
                        chosenProduct.reviews.map(element => {
                            return (
                                <div key={element.id} className="mt-5">
                                    {role === 'ROLE_ADMIN' ?
                                        <article className="close-button">
                                            <MDBBtn className='btn-close' onClick={toggleOpen} color="none" aria-label="Close" />
                                        </article>
                                        : null
                                    }
                                    <MDBModal open={basicModal} onClose={() => setBasicModal(false)} tabIndex='-1'>
                                        <MDBModalDialog>
                                            <MDBModalContent>
                                                <MDBModalHeader>
                                                    <MDBModalTitle>Review removal</MDBModalTitle>
                                                    <MDBBtn className='btn-close' color='none' onClick={toggleOpen}></MDBBtn>
                                                </MDBModalHeader>
                                                <MDBModalBody>Are you sure you want to remove that review?</MDBModalBody>

                                                <MDBModalFooter>
                                                    <MDBBtn color='secondary' onClick={toggleOpen}>
                                                        No
                                                    </MDBBtn>
                                                    <MDBBtn className="classic-button" onClick={() => removeFromDb(element.id)}>Yes</MDBBtn>
                                                </MDBModalFooter>
                                            </MDBModalContent>
                                        </MDBModalDialog>
                                    </MDBModal>
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