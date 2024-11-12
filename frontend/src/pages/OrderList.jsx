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

    function changeStatus(orderId, orderStatusId, orderStatus){
        const order = {
            id: orderStatusId,
            orderId: orderId
        };

        fetch(`http://localhost:8080/update-order-status`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(order)

        }).then(response => {
            if(response.ok){
                
            }
        })
    }

    return (<>
        <MDBContainer>
            <MDBTypography variant='h1 mt-2'>Your orders</MDBTypography>
        </MDBContainer>
        <MDBCard>
            <MDBListGroup flush>
                {!isLoading ?
                    orders.map(element => {
                        return (
                            <div key={element.id}>
                                <MDBListGroupItem>
                                    Order from: {element.date} - <MDBDropdown>
                                        <MDBDropdownToggle color='success'>{element.status}</MDBDropdownToggle>
                                        <MDBDropdownMenu>
                                            {orderStatuses.map(item => {
                                                return(<MDBDropdownItem key={item.id} link onClick={(e) => changeStatus(element.id, item.id, item.status)}>{item.status}</MDBDropdownItem>)
                                            })}
                                        </MDBDropdownMenu>
                                    </MDBDropdown>
                                </MDBListGroupItem>
                                {element.products.map(item => {
                                    return(<MDBListGroupItem>
                                        {item}
                                    </MDBListGroupItem>)
                                })}
                                <MDBListGroupItem>
                                    {!isLoading ?
                                        element.price : 0} ,-</MDBListGroupItem>
                            </div>)
                    })
                    :
                    <p>There are no orders yet.</p>
                }
            </MDBListGroup>
        </MDBCard>
    </>)
}