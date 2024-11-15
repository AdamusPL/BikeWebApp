import { MDBCard, MDBContainer, MDBTypography, MDBListGroup, MDBListGroupItem, MDBDropdown, MDBDropdownItem, MDBDropdownMenu, MDBDropdownToggle } from "mdb-react-ui-kit"
import { useEffect, useState } from "react"

export default function OrderList() {

    const [isLoading, setIsLoading] = useState(true);
    const [orders, setOrders] = useState([]);
    const [orderStatuses, setOrderStatuses] = useState([]);

    useEffect(() => {
        getOrderStatuses();
        getOrders();
        setIsLoading(false);
    }, []);

    async function getOrders() {
        const response = await fetch(`http://localhost:8080/get-all-orders-list`);
        const data = await response.json();
        setOrders(data);
    }

    async function getOrderStatuses() {
        const response = await fetch(`http://localhost:8080/get-order-statuses`);
        const data = await response.json();
        setOrderStatuses(data);
    }

    function changeStatus(order, status) {
        const orderDto = {
            id: status.id,
            orderId: order.id
        };

        fetch(`http://localhost:8080/update-order-status`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(orderDto)

        }).then(response => {
            if (response.ok) {
                debugger;
                
                let orderCopy = orders;
                let foundOrder = orderCopy.find(item => item.id === order.id);
                foundOrder.status = status.status;

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
                        return(
                        <MDBListGroup key={order.id}>
                            <MDBListGroupItem>
                                <MDBTypography tag='h4'>Order from: {order.date}</MDBTypography> <MDBDropdown>
                                    <MDBDropdownToggle color='success'> {order.status}</MDBDropdownToggle>
                                    <MDBDropdownMenu>
                                        {orderStatuses.map(status => {
                                            return (<MDBDropdownItem key={status.id} link onClick={(e) => changeStatus(order, status)}>{status.status}</MDBDropdownItem>)
                                        })}
                                    </MDBDropdownMenu>
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