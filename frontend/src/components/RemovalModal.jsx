import { MDBBtn, MDBModal, MDBModalBody, MDBModalContent, MDBModalDialog, MDBModalFooter, MDBModalHeader, MDBModalTitle } from "mdb-react-ui-kit"
import { useState } from "react"

export default function RemovalModal({ isBike, isReview, element, setProducts, setChosenProduct }) {
    const [basicModal, setBasicModal] = useState(false);
    const toggleOpen = () => setBasicModal(!basicModal);

    function removeBikeFromDb(id) {
        fetch(`http://localhost:8080/delete-bike?bikeId=${id}`, {
            credentials: 'include',
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    setProducts((prevItems) => prevItems.filter((item) => item.id !== id));
                    toggleOpen();
                }
            });
    }

    function removePartFromDb(id) {
        fetch(`http://localhost:8080/delete-part?partId=${id}`, {
            credentials: 'include',
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    setProducts((prevItems) => prevItems.filter((item) => item.id !== id));
                    toggleOpen();
                }
            });
    }

    function removeReviewFromDb(id) {
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
        <MDBBtn onClick={toggleOpen} className="btn-close" color="none" aria-label="Close" />
        <MDBModal open={basicModal} onClose={() => setBasicModal(false)} tabIndex='-1'>
            <MDBModalDialog>
                <MDBModalContent>
                    <MDBModalHeader>
                        <MDBModalTitle>Removal</MDBModalTitle>
                        <MDBBtn className='btn-close' color='none' onClick={toggleOpen}></MDBBtn>
                    </MDBModalHeader>
                    <MDBModalBody>Are you sure you want to remove it?</MDBModalBody>

                    <MDBModalFooter>
                        <MDBBtn color='secondary' onClick={toggleOpen}>
                            No
                        </MDBBtn>
                        {isReview ?
                            <MDBBtn className="classic-button" onClick={() => removeReviewFromDb(element.id)}>Yes</MDBBtn>
                            :
                            isBike ?
                                <MDBBtn className="classic-button" onClick={() => removeBikeFromDb(element.id)}>Yes</MDBBtn>
                                :
                                <MDBBtn className="classic-button" onClick={() => removePartFromDb(element.id)}>Yes</MDBBtn>
                        }
                    </MDBModalFooter>
                </MDBModalContent>
            </MDBModalDialog>
        </MDBModal>
    </>)
}