import React, { useEffect, useState } from 'react';
import {
    MDBRow,
    MDBCol,
    MDBContainer,
    MDBSpinner,
    MDBBtn,
    MDBCheckbox,
} from 'mdb-react-ui-kit';
import { useRole } from '../components/RoleProvider';
import '../css/PartShop.css'
import ProductCard from '../components/ProductCard';
import RemovalModal from '../components/RemovalModal';

export default function PartShop() {
    const [products, setProducts] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [filters, setFilters] = useState({});
    const { role } = useRole();

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
                    const found = cart.parts.find(cartItem => cartItem.id === item.id);
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
        const response = await fetch('http://localhost:8080/get-part-filters');
        const data = await response.json();

        setFilters(data);
    }

    function applyFilter(typeId) {
        setFilters(prevArray => ({
            ...prevArray,
            partTypeFilterDtos: prevArray.partTypeFilterDtos.map(type =>
                type.id === typeId
                    ? {
                        ...type,
                        isChecked: !type.isChecked
                    }
                    : type
            )
        }));
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

    async function filterChanged() {
        const response = await fetch(`http://localhost:8080/filter-parts-by-type`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(filters)
        });

        const data = await response.json();

        checkAvailability(data);
    }

    return (<>
        <MDBContainer fluid>
            <MDBRow className="h-100">
                <MDBCol id='sidebar' md="auto">
                    <p className='mt-4'>Type</p>
                    {!isLoading ?
                        filters?.partTypeFilterDtos?.length > 0 ?
                            filters.partTypeFilterDtos.map(element => (
                                <article key={element.id} className='mt-4'>
                                    <MDBCheckbox onClick={() => applyFilter(element.id)} key={element.id} name='flexCheck' value='' id='flexCheckDefault' label={element.type} />
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
                            <MDBBtn className="mt-4 classic-button" href='/add-part'>Add new part</MDBBtn>
                        </article>
                        : null
                    }

                    <MDBCol md="11">
                        <MDBRow className='row-cols-1 row-cols-md-3 g-4 mt-2'>
                            {!isLoading ?
                                products.length === 0 ?
                                    <p>Currently, we are out of stock</p>
                                    :
                                    products.map(element => (
                                        <MDBCol key={element.id}>
                                            {role === 'ROLE_ADMIN' ? <article className='close-button'>
                                                <RemovalModal isBike={false} isReview={false} element={element} setProducts={setProducts} setChosenProduct={null} />
                                            </article> : null}
                                            <ProductCard isBike={false} element={element} role={role} products={products} setProducts={setProducts} />
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