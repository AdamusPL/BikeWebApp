import { MDBCard, MDBContainer, MDBTypography, MDBListGroup, MDBListGroupItem, MDBDropdown, MDBDropdownItem, MDBDropdownMenu, MDBDropdownToggle } from "mdb-react-ui-kit"
import { useEffect, useState } from "react"
import { useRole } from "../components/RoleProvider";

import '../css/OrderList.css'

export default function OrderList() {

    const [isLoading, setIsLoading] = useState(true);
    const [orders, setOrders] = useState([]);
    const [orderStatuses, setOrderStatuses] = useState([]);
    const { isShopAssistant } = useRole();

    useEffect(() => {
        if (isShopAssistant) {
            getOrderStatuses();
            getAllOrders();
        }
        else {
            getClientOrders();
        }
        setIsLoading(false);
    }, [isShopAssistant]);

    async function getAllOrders() {
        const response = await fetch(`http://localhost:8080/get-all-orders-list`,
            { credentials: 'include' }
        );
        const data = await response.json();
        setOrders(data);
    }

    async function getOrderStatuses() {
        const response = await fetch(`http://localhost:8080/get-order-statuses`,
            { credentials: 'include' }
        );
        const data = await response.json();
        setOrderStatuses(data);
    }

    async function getClientOrders() {
        const response = await fetch(`http://localhost:8080/get-order-list`,
            { credentials: 'include' }
        );
        const data = await response.json();
        setOrders(data);
    }

    function changeStatus(orderToUpdate, updatedStatus) {
        const orderDto = {
            id: updatedStatus.id,
            orderId: orderToUpdate.id
        };

        fetch(`http://localhost:8080/update-order-status`, {
            credentials: 'include',
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(orderDto)

        }).then(response => {
            if (response.ok) {
                let orderCopy = orders.map(order =>
                    order.id === orderToUpdate.id ? { ...order, status: updatedStatus.status } : order
                );

                setOrders(orderCopy);
            }
        })
    }

    return (<>
        <MDBContainer>
            {isShopAssistant ?
                <MDBTypography variant='h1 mt-4'>Here you can manage customer orders</MDBTypography>
                : <MDBTypography variant='h1 mt-4'>Your orders</MDBTypography>
            }
            {!isLoading ?
                orders.map(order => {
                    return (
                        <MDBCard key={order.id} className='mt-4'>
                            <MDBListGroup>
                                <MDBListGroupItem>
                                    <article className='order'>
                                        <MDBTypography tag='h4'>Order from: {order.date}</MDBTypography>
                                        <MDBDropdown>
                                            {isShopAssistant ?
                                                <article>
                                                    <MDBDropdownToggle color='success'> {order.status}</MDBDropdownToggle>
                                                    <MDBDropdownMenu>
                                                        {orderStatuses.map(status => {
                                                            return (<MDBDropdownItem key={status.id} link onClick={(e) => changeStatus(order, status)}>{status.status}</MDBDropdownItem>)
                                                        })}
                                                    </MDBDropdownMenu>
                                                </article>
                                                : <p>{order.status}</p>
                                            }
                                        </MDBDropdown>
                                    </article>
                                </MDBListGroupItem>

                                {order.orderedBikes.map(bike => {
                                    return (<MDBListGroupItem className="mb-4" key={bike.id}>
                                        {bike.fullname}, {bike.quantity} x {bike.price} zł
                                    </MDBListGroupItem>)
                                })}

                                {order.orderedParts.map(part => {
                                    return (<MDBListGroupItem className="mb-4" key={part.id}>
                                        {part.fullname}, {part.quantity} x {part.price} zł
                                    </MDBListGroupItem>)
                                })}

                                <MDBListGroupItem>
                                    <MDBTypography tag='h4'>Summary</MDBTypography>
                                </MDBListGroupItem>

                                {!isLoading ?
                                    <MDBListGroupItem>
                                        <MDBTypography tag='dt' sm='3' color="success">{order.price} zł </MDBTypography>
                                    </MDBListGroupItem>
                                    : <p>0, -</p>}

                            </MDBListGroup>
                        </MDBCard>
                    )
                })
                :
                <p>There are no orders yet.</p>
            }

        </MDBContainer >
    </>)
}