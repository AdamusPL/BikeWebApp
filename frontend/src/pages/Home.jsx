import { MDBContainer } from 'mdb-react-ui-kit';
import React from 'react';

import '../css/Home.css'

export default function Home() {
    return (<>
        <MDBContainer>
            <figure id='image' className='bg-image'>
                <img
                    src="https://mdbcdn.b-cdn.net/img/new/slides/041.webp"
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