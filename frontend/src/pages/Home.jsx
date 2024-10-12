import { MDBContainer } from 'mdb-react-ui-kit';
import React from 'react';

export default function Home() {
    return (<>
        <MDBContainer>
            <figure className='bg-image mt-2' style={{ position: 'relative', display: 'inline-block' }}>
                <img
                    src='https://mdbootstrap.com/img/new/standard/city/041.webp'
                    className='img-fluid rounded shadow-3'
                    alt='...'
                />
                <div className='mask' style={{ backgroundColor: 'rgba(0, 0, 0, 0.6)'}}>
                    <div className='d-flex justify-content-center align-items-center h-100'>
                        <p className='text-white mb-0'>Welcome to BikeParadise Shop!</p>
                    </div>
                </div>
            </figure>
            <figcaption className='figure-caption'>Founded in 2024, aiming to provide you the professional experience. Here, you will find bikes from makes such as: AeroBike, LizardCycle, Phumay and more! Moreover, we provide parts from manufacturers like: SwiftZPart, PFB and ClunkyJ</figcaption>
        </MDBContainer>
    </>)
}