import { MDBContainer, MDBTypography } from "mdb-react-ui-kit";

export default function Unauthorized(){
    return(<>
        <MDBContainer>
            <MDBTypography>401 Unauthorized</MDBTypography>
        </MDBContainer>
    </>)
}