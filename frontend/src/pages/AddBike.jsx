import { MDBContainer, MDBInput, MDBDropdown, MDBDropdownToggle, MDBDropdownItem, MDBDropdownMenu, MDBTextArea, MDBBtn, MDBTypography } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";

export default function AddBike() {

    const [filters, setFilters] = useState([]);
    const [keysArray, setKeysArray] = useState([]);
    const [defaultValues, setDefaultValues] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        getFilters();
        setIsLoading(false);
    }, [])

    async function getFilters() {
        const response = await fetch('http://localhost:8080/get-add-bike-filters');
        const data = await response.json();

        let defaultValues = [];

        const keysArray = Object.keys(data);
        keysArray.map(element => {
            const object = {
                parameter: element,
                attribute: data[element][0]
            }
            defaultValues.push(object);
        })
        setKeysArray(keysArray);
        setFilters(data);
        setDefaultValues(defaultValues);
    }

    function changeParameter(parameter, attribute) {
        setDefaultValues(prevData =>
            prevData.map(item =>
                item.parameter === parameter ? { ...item, attribute: attribute } : item
            )
        );
    }

    return (<>
        <MDBContainer>
            <MDBTypography variant='h1 mt-2'>Add new bike</MDBTypography>

            <MDBInput label="Model Name" id="typeText" type="text" className="mt-5" onChange={(e) => { setFirstName(e.target.value) }} />

            <MDBInput label="Price" id="typeText" type="text" className="mt-5" onChange={(e) => { setUsername(e.target.value) }} />

            <MDBTextArea label="Description" id="textAreaExample" rows="{4}" onChange={(e) => { setOpinion(e.target.value) }} />

            {!isLoading ?
                keysArray.map((element, index) => {
                    return (
                        <div class="d-flex align-items-center mt-2">
                            <MDBDropdown key={element}>
                                <MDBDropdownToggle color='success'>{element}</MDBDropdownToggle>
                                <MDBDropdownMenu>
                                    {filters[element].map(item => (
                                        <div>
                                            <a><MDBDropdownItem key={item} onClick={() => changeParameter(element, item)}>{item}</MDBDropdownItem></a>
                                        </div>
                                    ))}
                                </MDBDropdownMenu>
                            </MDBDropdown>
                            <a>{defaultValues[index].attribute}</a>
                        </div>)
                })
                :
                <p>Nothing found</p>
            }
            <MDBBtn color="success" className="mt-4">Add bike</MDBBtn>
        </MDBContainer>
    </>)
}