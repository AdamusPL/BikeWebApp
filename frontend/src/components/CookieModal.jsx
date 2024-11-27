import React, { useEffect, useState } from 'react';
import { MDBBtn, MDBModal, MDBModalDialog, MDBModalContent, MDBModalBody } from 'mdb-react-ui-kit';

export default function CookieModal() {
    const [bottomModal, setBottomModal] = useState(false);
    const toggleOpen = () => setBottomModal(!bottomModal);

    useEffect(() => {
        checkCookiesConfirmation();
    }, []);

    function checkCookiesConfirmation(){
        if(localStorage.getItem('cookiesConfirmed') === null || localStorage.getItem('cookiesConfirmed') === false){
            toggleOpen();
        }
    }

    function confirmCookies(){
        localStorage.setItem('cookiesConfirmed', true);
        toggleOpen();
    }

    return (
        <>
            <MDBModal animationDirection='bottom' open={bottomModal} tabIndex='-1' onClose={() => setBottomModal(false)}>
                <MDBModalDialog position='bottom' frame>
                    <MDBModalContent>
                        <MDBModalBody className='py-1'>
                            <div className='d-flex justify-content-center align-items-center my-3'>
                                <p className='mb-0'>We use cookies to improve your website experience</p>
                                <MDBBtn color='success' size='sm' className='ms-2' onClick={confirmCookies}>
                                    Ok, thanks
                                </MDBBtn>
                                <MDBBtn size='sm' className='ms-2'>
                                    Learn more
                                </MDBBtn>
                            </div>
                        </MDBModalBody>
                    </MDBModalContent>
                </MDBModalDialog>
            </MDBModal>
        </>
    );
}