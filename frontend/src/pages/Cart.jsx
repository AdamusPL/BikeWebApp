import { MDBCard, MDBListGroup, MDBListGroupItem, MDBTypography, MDBBtn, MDBContainer } from "mdb-react-ui-kit";

export default function Cart() {
    return (<>
        <MDBContainer>
            <MDBTypography variant='h1 mt-2'>Cart</MDBTypography>
            <MDBCard>
                <MDBListGroup flush>
                    <MDBListGroupItem>Item<MDBBtn className="btn-close" color="none" aria-label="Close" /></MDBListGroupItem>
                </MDBListGroup>
            </MDBCard>
        </MDBContainer>
    </>)
}