import { MDBContainer, MDBInput, MDBDropdown, MDBDropdownToggle, MDBDropdownItem, MDBDropdownMenu, MDBTextArea, MDBBtn, MDBTypography } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";

export default function AddPart() {
    const [filters, setFilters] = useState([]);
    const [keysArray, setKeysArray] = useState([]);
    const [defaultValues, setDefaultValues] = useState([]);
    const [attributes, setAttributes] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    const [make, setMake] = useState("");
    const [modelName, setModelName] = useState("");
    const [price, setPrice] = useState("");
    const [description, setDescription] = useState("");
    const [quantityInStock, setQuantityInStock] = useState(0);

    const [isPosted, setIsPosted] = useState(false);

    useEffect(() => {
        getFilters();
        setIsLoading(false);
    }, [])

    async function getFilters() {
        const response = await fetch('http://localhost:8080/get-part-filters');
        const data = await response.json();
        setFilters(data);

        const keysArray = Object.keys(data);

        setKeysArray(keysArray);
        setAttributes(data[keysArray[0]]);
        setFilters(data);
        setDefaultValues({ type: keysArray[0], attribute: data[keysArray[0]][0]});
        console.log(attributes);
    }

    async function addPartToDB() {
        const part = {
            make: make,
            modelName: modelName,
            price: price,
            quantityInStock: quantityInStock,
            description: description,
            type: defaultValues.type,
            attribute: defaultValues.attribute,
            shopAssistantId: 1
        }

        fetch(`http://localhost:8080/add-part`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(part)

        }).then(response => {
            if (response.ok) {
                setIsPosted(true);
            }
        })
    }

    function changeType(type) {
        debugger;
        setDefaultValues(prevData => ({
            type: type,
            attribute: filters[type][0]
        }));
        setAttributes(filters[type]);
    }

    function changeAttribute(attribute) {
        setDefaultValues(prevData => ({
            ...prevData,
            attribute: attribute
        }));
    }

    return (<>
        <MDBContainer>
            <MDBTypography variant='h1 mt-2'>Add new part</MDBTypography>

            <MDBInput label="Make" id="typeText" type="text" className="mt-5" onChange={(e) => { setMake(e.target.value) }} />

            <MDBInput label="Model Name" id="typeText" type="text" className="mt-5" onChange={(e) => { setModelName(e.target.value) }} />

            <MDBInput label="Price" id="typeText" type="text" className="mt-5" onChange={(e) => { setPrice(e.target.value) }} />

            <MDBTextArea label="Description" id="textAreaExample" className="mt-5" rows="{4}" onChange={(e) => { setDescription(e.target.value) }} />

            <MDBInput label="Quantity in stock" id="typeText" type="text" className="mt-5" onChange={(e) => { setQuantityInStock(e.target.value) }} />


            {!isLoading ?
                <div class="d-flex align-items-center mt-2">
                    <MDBDropdown>
                        <MDBDropdownToggle color='success'>Type</MDBDropdownToggle>
                        <MDBDropdownMenu>
                            {keysArray.map(item => (
                                <div>
                                    <a><MDBDropdownItem key={item} onClick={() => changeType(item)}>{item}</MDBDropdownItem></a>
                                </div>
                            ))}
                        </MDBDropdownMenu>
                    </MDBDropdown>
                    {defaultValues.type}
                </div>
                :
                <p>No data found</p>
            }

            {!isLoading ?
                <div class="d-flex align-items-center mt-2">
                    <MDBDropdown>
                        <MDBDropdownToggle color='success'>Attribute</MDBDropdownToggle>
                        <MDBDropdownMenu>
                            {attributes.map(item => (
                                <div>
                                    <a><MDBDropdownItem key={item} onClick={() => changeAttribute(item)}>{item}</MDBDropdownItem></a>
                                </div>
                            ))}
                        </MDBDropdownMenu>
                    </MDBDropdown>
                    {defaultValues.attribute}
                </div>
                :
                <p>No data found</p>
            }

            <MDBBtn onClick={addPartToDB} color="success" className="mt-4">Add part</MDBBtn>

            {isPosted ? <p>Part successfully added</p>
                :
                <p></p>}

        </MDBContainer>
    </>)
}