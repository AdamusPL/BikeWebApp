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
                        <MDBModalBody className=''>
                            <div className='d-flex justify-content-center align-items-center my-3'>
                                <p className='mb-0'>This page use cookies to provide services at highest level. Further using that page means you agree to use them.</p>
                                <MDBBtn color='success' className='ms-2' onClick={confirmCookies}>
                                    OK
                                </MDBBtn>
                            </div>
                        </MDBModalBody>
                    </MDBModalContent>
                </MDBModalDialog>
            </MDBModal>
        </>
    );
}