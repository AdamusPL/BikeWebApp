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

    async function getFilters() {
        const response = await fetch('http://localhost:8080/get-part-filters');
        const data = await response.json();

        var defaultValues = [];

        const keysArray = Object.keys(data);
        defaultValues.push({ parameter: 'Type', attribute: keysArray[0] });
        defaultValues.push({ parameter: 'Attribute', attribute: data[keysArray[0]][0] });

        setKeysArray(keysArray);
        setAttributes(data[keysArray[0]]);
        setFilters(data);
        setDefaultValues(defaultValues);
        console.log(defaultValues[0].attribute);
        console.log(defaultValues[1].attribute);
    }

    async function addPartToDB() {
        debugger;
        const part = {
            make: make,
            modelName: modelName,
            price: price,
            quantityInStock: quantityInStock,
            description: description,
            type: defaultValues[0].attribute,
            attribute: defaultValues[1].attribute,
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

    function changeParameter(parameter, attribute) {
        setDefaultValues(prevData =>
            prevData.map(item =>
                item.parameter === parameter ? { ...item, attribute: attribute } : item
            )
        );
    }

    useEffect(() => {
        getFilters();
        setIsLoading(false);
    }, [])

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
                                    <a><MDBDropdownItem key={item} onClick={() => changeParameter('Type', item)}>{item}</MDBDropdownItem></a>
                                </div>
                            ))}
                        </MDBDropdownMenu>
                    </MDBDropdown>
                    {defaultValues.map((element, index) => {
                        return (<a>{index === 0 ? element.attribute : null}</a>)
                    })}
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
                                    <a><MDBDropdownItem key={item} onClick={() => changeParameter('Attribute', item)}>{item}</MDBDropdownItem></a>
                                </div>
                            ))}
                        </MDBDropdownMenu>
                    </MDBDropdown>
                    {defaultValues.map((element, index) => {
                        return (<a>{index === 1 ? element.attribute : null}</a>)
                    })}
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