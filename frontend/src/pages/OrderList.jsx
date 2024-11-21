import { MDBCard, MDBContainer, MDBTypography, MDBListGroup, MDBListGroupItem, MDBDropdown, MDBDropdownItem, MDBDropdownMenu, MDBDropdownToggle } from "mdb-react-ui-kit"
import { useEffect, useState } from "react"
import { useRole } from "../components/RoleProvider";

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
        debugger;
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
        const clientId = 1;
        const response = await fetch(`http://localhost:8080/get-order-list?clientId=${clientId}`,
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
                debugger;

                let orderCopy = orders.map(order =>
                    order.id === orderToUpdate.id ? { ...order, status: updatedStatus.status } : order
                );

                setOrders(orderCopy);
            }
        })
    }

    return (<>
        <MDBContainer>
            <MDBTypography variant='h1 mt-2'>Your orders</MDBTypography>
            <MDBCard>
                {!isLoading ?
                    orders.map(order => {
                        return (
                            <MDBListGroup key={order.id}>
                                <MDBListGroupItem>
                                    <MDBTypography tag='h4'>Order from: {order.date}</MDBTypography> <MDBDropdown>
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
                                </MDBListGroupItem>

                                {order.orderedBikes.map(bike => {
                                    return (<MDBListGroupItem className="mb-4" key={bike.id}>
                                        {bike.fullname}
                                    </MDBListGroupItem>)
                                })}

                                {order.orderedParts.map(part => {
                                    return (<MDBListGroupItem className="mb-4" key={part.id}>
                                        {part.fullname}
                                    </MDBListGroupItem>)
                                })}

                                {!isLoading ?
                                    <MDBListGroupItem>
                                        {order.price} ,-
                                    </MDBListGroupItem>
                                    : <p>0, -</p>}

                            </MDBListGroup>)
                    })
                    :
                    <p>There are no orders yet.</p>
                }
            </MDBCard>
        </MDBContainer >
    </>)
}