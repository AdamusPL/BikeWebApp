import { MDBTypography } from "mdb-react-ui-kit";

import '../css/Unauthorized.css'

export default function Unauthorized() {
    return (<>
        <MDBTypography tag='div' id="error-info" className='display-1 pb-3 mb-3 border-bottom'>
            401 Unauthorized
        </MDBTypography>
    </>)
}