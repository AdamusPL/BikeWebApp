import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { MDBContainer, MDBRow, MDBCol, MDBBtn, MDBInput, MDBTextArea, MDBSpinner } from "mdb-react-ui-kit";
import '../css/BikeDetails.css'
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";

export default function BikeDetails() {

    const urlParameters = useParams();
    const [chosenProduct, setChosenProduct] = useState({});
    const [keysArray, setKeysArray] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    const [numberOfStars, setNumberOfStars] = useState(0);
    const [opinion, setOpinion] = useState("");

    const [isReviewPosted, setIsReviewPosted] = useState(false);

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

    useEffect(() => {
        getDetailedInfo();
    }, []);

    return (<>
        <MDBContainer>
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
                    <MDBBtn className="ms-3" color="success">Add to cart</MDBBtn>
                    <p className="mt-2">Quantity in stock: {chosenProduct.quantityInStock}</p>
                </MDBCol>
            </MDBRow>
            <p>{chosenProduct.description}</p>

            <p className="fw-light">Reviews</p>
            <p className="fw-lighter">Write a review</p>
            <a><MDBInput label="1-5" onChange={(e) => { setNumberOfStars(e.target.value) }}></MDBInput></a>
            <a>/5</a>
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

        </MDBContainer>
    </>)
}