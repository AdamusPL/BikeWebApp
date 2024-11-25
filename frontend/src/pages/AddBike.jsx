import { MDBContainer, MDBInput, MDBDropdown, MDBDropdownToggle, MDBDropdownItem, MDBDropdownMenu, MDBTextArea, MDBBtn, MDBTypography } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";
import '../css/AddBike.css';
import { useNavigate } from "react-router-dom";

export default function AddBike() {

    const [filters, setFilters] = useState([]);
    const [keysArray, setKeysArray] = useState([]);
    const [defaultValues, setDefaultValues] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    const [modelName, setModelName] = useState("");
    const [price, setPrice] = useState("");
    const [description, setDescription] = useState("");
    const [serialNumbers, setSerialNumbers] = useState("");

    const [isPosted, setIsPosted] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        getFilters();
        setIsLoading(false);
    }, [])

    async function getFilters() {
        const response = await fetch('http://localhost:8080/get-add-bike-filters', { credentials: 'include' });
        debugger;

        if(response.status === 401){
            navigate('/unauthorized');
        }

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

    async function addPartToDB() {
        let bike = {
            modelName: modelName,
            price: price,
            description: description,
            bikeIdentificationsAvailable: serialNumbers,
            parts: defaultValues,
            shopAssistantId: 1
        }

        fetch(`http://localhost:8080/add-bike`, {
            credentials: 'include',
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(bike)

        }).then(response => {
            if (response.ok) {
                setIsPosted(true);
            }
        })
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

            <MDBInput label="Model Name" id="typeText" type="text" className="mt-5" onChange={(e) => { setModelName(e.target.value) }} />

            <MDBInput label="Price" id="typeText" type="text" className="mt-5" onChange={(e) => { setPrice(e.target.value) }} />

            <MDBTextArea label="Description" id="textAreaExample" className="mt-5" rows="{4}" onChange={(e) => { setDescription(e.target.value) }} />

            <MDBInput label="Serial numbers" id="typeText" type="text" className="mt-5 mb-5" onChange={(e) => { setSerialNumbers(e.target.value) }} />

            {!isLoading ?
                keysArray.map((element, index) => {
                    return (
                        <div key={element} className='choice'>
                            <MDBDropdown className='margin-item'>
                                <MDBDropdownToggle className='classic-button'>{element}</MDBDropdownToggle>
                                <MDBDropdownMenu>
                                    {filters[element].map(item => (
                                        <MDBDropdownItem key={item} onClick={() => changeParameter(element, item)}>{item}</MDBDropdownItem>
                                    ))}
                                </MDBDropdownMenu>
                            </MDBDropdown>
                            <p className='value'>{defaultValues[index].attribute}</p>
                        </div>)
                })
                :
                <p>Nothing found</p>
            }
            <MDBBtn onClick={addPartToDB} className="mt-4 classic-button">Add bike</MDBBtn>

            {isPosted ? <p>Bike successfully added</p>
                :
                null}
        </MDBContainer>
    </>)
}