import { MDBBtn, MDBModal, MDBModalBody, MDBModalContent, MDBModalDialog, MDBModalFooter, MDBModalHeader, MDBModalTitle } from "mdb-react-ui-kit";
import { useState } from "react";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";

export default function BuyModal() {
    const [basicModal, setBasicModal] = useState(false);
    const [cookies] = useCookies(['token']);
    const toggleOpen = () => setBasicModal(!basicModal);

    const navigate = useNavigate();

    function handleClick() {
        if (cookies.token === undefined) {
            navigate('/sign-in');
        }
        else{
            toggleOpen();
        }
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
    </>)
}