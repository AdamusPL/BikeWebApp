import { MDBContainer, MDBTypography } from "mdb-react-ui-kit";

export default function NotFound(){
    return(<>
        <MDBTypography tag='div' id="error-info" className='display-1 pb-3 mb-3 border-bottom'>
            404 Not Found
        </MDBTypography>
    </>)
}