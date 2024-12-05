import { MDBBtn, MDBCard, MDBCardBody, MDBCardImage, MDBCardTitle, MDBCol, MDBRipple, MDBRow, MDBTypography } from "mdb-react-ui-kit";
import { useState } from "react"
import { Link } from "react-router-dom";
import Dialog from "./Dialog";


export default function ProductCard({ isBike, element, role, products, setProducts }) {
    const [isDialogOpen, setIsDialogOpen] = useState(false);

    function closeDialog() {
        setIsDialogOpen(!isDialogOpen);
    }

    function addBikeToCart(id) {
        if (localStorage.getItem('cart') === null) {
            localStorage.setItem('cart', JSON.stringify({ bikes: [{ id: id, quantity: 1 }], parts: [] }));
        }
        else {
            let cart = JSON.parse(localStorage.getItem('cart'));
            const index = cart.bikes.findIndex(b => b.id === id);

            if (index !== -1) {
                cart.bikes[index].quantity += 1;
            }

            else {
                cart = {
                    ...cart,
                    bikes: [...cart.bikes, { id: id, quantity: 1 }]
                };
            }

            localStorage.setItem('cart', JSON.stringify(cart));
        }

        let cartAfterModifying = JSON.parse(localStorage.getItem('cart'));

        let productsCopy = products;
        const product = productsCopy.find(item => item.id === id);

        const index = cartAfterModifying.bikes.findIndex(b => b.id === id);

        if (cartAfterModifying.bikes[index].quantity >= product.quantityInStock) {
            product.isAvailable = false;
        }

        setProducts(productsCopy);

        setIsDialogOpen(!isDialogOpen);
    }

    function addPartToCart(id) {
        if (localStorage.getItem('cart') === null) {
            localStorage.setItem('cart', JSON.stringify({ bikes: [], parts: [{ id: id, quantity: 1 }] }));
        }
        else {
            let cart = JSON.parse(localStorage.getItem('cart'));
            const index = cart.parts.findIndex(b => b.id === id);

            if (index !== -1) {
                cart.parts[index].quantity += 1;
            }

            else {
                cart = {
                    ...cart,
                    parts: [...cart.parts, { id: id, quantity: 1 }]
                };
            }

            localStorage.setItem('cart', JSON.stringify(cart));
        }

        let cartAfterModifying = JSON.parse(localStorage.getItem('cart'));

        let productsCopy = products;
        const product = productsCopy.find(item => item.id === id);

        const index = cartAfterModifying.parts.findIndex(b => b.id === id);

        if (cartAfterModifying.parts[index].quantity >= product.quantityInStock) {
            product.isAvailable = false;
        }

        setProducts(productsCopy);

        setIsDialogOpen(!isDialogOpen);

    }

    return (<>
        <MDBCard>
            {isBike ?
                <Link to={`/bike-shop/${element.id}`}>
                    <MDBRipple rippleColor='light' rippleTag='div' className='bg-image hover-overlay'>
                        <MDBCardImage
                            src="https://mdbcdn.b-cdn.net/img/new/slides/041.webp"
                            alt='...'
                            position='top'
                            width='300px'
                        />
                    </MDBRipple>
                </Link>
                :
                <Link to={`/part-shop/${element.id}`}>
                    <MDBRipple rippleColor='light' rippleTag='div' className='bg-image hover-overlay'>
                        <MDBCardImage
                            src="https://mdbcdn.b-cdn.net/img/new/slides/041.webp"
                            alt='...'
                            position='top'
                            width='300px'
                        />
                    </MDBRipple>
                </Link>
            }

            <MDBCardBody>
                <MDBCardTitle tag='h2' className='mb-4'>{element.make} {element.modelName}</MDBCardTitle>

                <MDBRow tag='dl'>
                    <MDBCol tag='dt'>
                        Type:
                    </MDBCol>
                    <MDBCol tag='dd'>
                        {element.type}
                    </MDBCol>
                </MDBRow>

                {isBike ?
                    <MDBRow tag='dl'>
                        <MDBCol tag='dt'>
                            Drivetrain:
                        </MDBCol>
                        <MDBCol tag='dd'>
                            {element.drive}
                        </MDBCol>
                    </MDBRow>
                    :
                    <MDBRow tag='dl'>
                        <MDBCol tag='dt'>
                            Kind:
                        </MDBCol>
                        <MDBCol tag='dd'>
                            {element.attribute}
                        </MDBCol>
                    </MDBRow>}

                <MDBRow tag='dl'>
                    <MDBCol tag='dt'>
                        Price:
                    </MDBCol>
                    <MDBCol tag='dd'>
                        {element.price}
                    </MDBCol>
                </MDBRow>
                <MDBRow tag='dl'>
                    <MDBCol tag='dt'>
                        Quantity in stock:
                    </MDBCol>
                    <MDBCol tag='dd'>
                        {element.quantityInStock}
                    </MDBCol>
                </MDBRow>

                {role !== 'ROLE_ADMIN' ?
                    element.isAvailable ?
                        isBike ? <Dialog isOpen={isDialogOpen} toggleOpen={() => addBikeToCart(element.id)} toggleClose={closeDialog} />
                            : <Dialog isOpen={isDialogOpen} toggleOpen={() => addPartToCart(element.id)} toggleClose={closeDialog} />
                        :
                        <article>
                            <MDBBtn className='me-1' color='secondary'>
                                Add to cart
                            </MDBBtn>
                            <MDBTypography tag='dt' sm='3' className='mt-2'>It's not available anymore!</MDBTypography>
                        </article>
                    : null
                }
            </MDBCardBody>
        </MDBCard>
    </>)
}