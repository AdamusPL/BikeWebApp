import React, { useEffect, useState } from 'react';
import {
    MDBRow,
    MDBCol,
    MDBContainer,
    MDBSpinner,
    MDBBtn,
    MDBCheckbox,
    MDBModalDialog,
    MDBModalContent,
    MDBModalHeader,
    MDBModalTitle,
    MDBModalBody,
    MDBModalFooter,
    MDBModal
} from 'mdb-react-ui-kit';

import '../css/BikeShop.css'
import { useRole } from '../components/RoleProvider';
import ProductCard from '../components/ProductCard';

export default function BikeShop() {
    const [products, setProducts] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [filters, setFilters] = useState({});
    const { role } = useRole();

    const [basicModal, setBasicModal] = useState(false);
    const toggleOpen = () => setBasicModal(!basicModal);

    useEffect(() => {
        getFilters();
        setIsLoading(false);
    }, []);

    useEffect(() => {
        if (!isLoading) {
            filterChanged();
        }
    }, [filters]);

    function checkAvailability(data) {
        const cart = JSON.parse(localStorage.getItem('cart'));
        data.map(item => {
            item.isAvailable = true;
            if (item.quantityInStock === 0) {
                item.isAvailable = false;
            }
            else {
                if (cart) {
                    const found = cart.bikes.find(cartItem => cartItem.id === item.id);
                    item.isAvailable = true;
                    if (found) {
                        if (found.quantity >= item.quantityInStock || found.quantity === 0) {
                            item.isAvailable = false;
                        }
                    }
                }
            }
        })
        setProducts(data);
    }

    async function getFilters() {
        const response = await fetch('http://localhost:8080/get-bike-shop-filters');
        const data = await response.json();

        setFilters(data);
    }

    function removeFromDb(id) {
        fetch(`http://localhost:8080/delete-bike?bikeId=${id}`, {
            credentials: 'include',
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    setProducts((prevItems) => prevItems.filter((item) => item.id !== id));
                    toggleOpen();
                }
            });
    }

    function applyFilter(typeId, attributeId) {
        setFilters(prevFilters => ({
            ...prevFilters,
            bikeFilterCheckboxDtos: prevFilters.bikeFilterCheckboxDtos.map(type =>
                type.id === typeId
                    ? {
                        ...type,
                        attribute: type.attribute.map(attribute =>
                            attribute.id === attributeId
                                ? { ...attribute, isChecked: !attribute.isChecked }
                                : attribute
                        )
                    }
                    : type
            )
        }));
    }

    async function filterChanged() {
        const response = await fetch(`http://localhost:8080/filter-bikes`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(filters)
        });

        const data = await response.json();

        checkAvailability(data);
    }

    function applyMinPrice(e){
        setFilters(prevArray => ({
            ...prevArray,
            minPrice: e.target.value
        }));
    }

    function applyMaxPrice(e){
        setFilters(prevArray => ({
            ...prevArray,
            maxPrice: e.target.value
        }));
    }

    return (<>
        <MDBContainer fluid>
            <MDBRow className="h-100">
                <MDBCol id='sidebar' md="auto">
                    {!isLoading ?
                        filters?.bikeFilterCheckboxDtos?.length > 0 ?
                            filters.bikeFilterCheckboxDtos.map(element => (
                                <article key={element.id} className='mt-4'>
                                    <p>{element.type}</p>
                                    {element.attribute.map(item => (
                                        <MDBCheckbox onClick={() => applyFilter(element.id, item.id)} key={item.id} name='flexCheck' value='' id='flexCheckDefault' label={item.attribute} />
                                    ))}
                                </article>
                            ))
                            :
                            null
                        :
                        <p>No filters available</p>
                    }

                    <p className='mt-4'>Price</p>
                    <article id='price' className='mb-4'>
                        <input className='form-control input' maxLength="8" onChange={applyMinPrice} defaultValue={filters.minPrice}></input>
                        <p id='minus'>-</p>
                        <input className='form-control input' maxLength="8" onChange={applyMaxPrice} defaultValue={filters.maxPrice}></input>
                    </article>
                </MDBCol>

                <MDBCol>
                    {role === 'ROLE_ADMIN' ?
                        <article id="button">
                            <MDBBtn className="mt-4 classic-button" href='/add-bike'>Add new bike</MDBBtn>
                        </article> : null}

                    <MDBCol md="11">
                        <MDBRow className='row-cols-1 row-cols-md-3 g-4 mt-2'>
                            {!isLoading ?
                                products.length === 0 ?
                                    <p>Currently, we are out of stock</p>
                                    :
                                    products.map(element => (
                                        <MDBCol key={element.id}>
                                            {role === 'ROLE_ADMIN' ? <article className='close-button'>
                                                <MDBBtn onClick={toggleOpen} className="btn-close" color="none" aria-label="Close" />
                                                <MDBModal open={basicModal} onClose={() => setBasicModal(false)} tabIndex='-1'>
                                                    <MDBModalDialog>
                                                        <MDBModalContent>
                                                            <MDBModalHeader>
                                                                <MDBModalTitle>Product removal</MDBModalTitle>
                                                                <MDBBtn className='btn-close' color='none' onClick={toggleOpen}></MDBBtn>
                                                            </MDBModalHeader>
                                                            <MDBModalBody>Are you sure you want to remove that product?</MDBModalBody>

                                                            <MDBModalFooter>
                                                                <MDBBtn color='secondary' onClick={toggleOpen}>
                                                                    No
                                                                </MDBBtn>
                                                                <MDBBtn className="classic-button" onClick={() => removeFromDb(element.id)}>Yes</MDBBtn>
                                                            </MDBModalFooter>
                                                        </MDBModalContent>
                                                    </MDBModalDialog>
                                                </MDBModal>
                                            </article> : null}

                                            <ProductCard isBike={true} element={element} role={role} products={products} setProducts={setProducts} />
                                        </MDBCol>
                                    ))
                                :
                                <article>
                                    <MDBSpinner role='status'>
                                        <span className='visually-hidden'>Loading...</span>
                                    </MDBSpinner>
                                </article>
                            }
                        </MDBRow>
                    </MDBCol>
                </MDBCol >
            </MDBRow >
        </MDBContainer>
    </>);
}