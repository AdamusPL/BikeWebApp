import {
    MDBBtn,
    MDBModal,
    MDBModalDialog,
    MDBModalContent,
    MDBModalHeader,
    MDBModalTitle,
    MDBModalBody,
    MDBModalFooter,
  } from 'mdb-react-ui-kit';

export default function Dialog({ isOpen, toggleOpen, toggleClose}) {
    return (<>
        <MDBBtn className='classic-button' onClick={toggleOpen}>Add to cart</MDBBtn>

        <MDBModal
            animationDirection='right'
            open={isOpen}
            tabIndex='-1'
            onClose={() => setTopRightModal(false)}
        >
            <MDBModalDialog position='top-right' side>
                <MDBModalContent>
                    <MDBModalHeader className='text-white classic-button'>
                        <MDBModalTitle>Product in the cart</MDBModalTitle>
                        <MDBBtn
                            color='none'
                            className='btn-close btn-close-white'
                            onClick={toggleClose}
                        ></MDBBtn>
                    </MDBModalHeader>
                    <MDBModalBody>
                        <div className='row'>
                            <div className='col-3 text-center'>
                                <i className='fas fa-shopping-cart fa-4x button-out'></i>
                            </div>

                            <div className='col-9'>
                                <p>Do you need more time to make a purchase decision?</p>
                                <p>No pressure, your product will be waiting for you in the cart.</p>
                            </div>
                        </div>
                    </MDBModalBody>
                    <MDBModalFooter>
                        <MDBBtn className='classic-button' href='/cart'>Go to the cart</MDBBtn>
                        <MDBBtn outline className='button-out' onClick={toggleClose}>
                            Close
                        </MDBBtn>
                    </MDBModalFooter>
                </MDBModalContent>
            </MDBModalDialog>
        </MDBModal>
    </>)
}