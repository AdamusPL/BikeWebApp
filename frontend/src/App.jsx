import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Layout } from './components/Layout';
import { Home } from './pages/Home.jsx';

function App() {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path='/' element={<Home />}></Route>
          <Route path='/bike-shop' element={BikeShop}></Route>
          <Route path='/part-shop' element={PartShop}></Route>
          <Route path='/bike-details' element={BikeDetails}></Route>
          <Route path='/part-details' element={PartDetails}></Route>
          <Route path='/basket' element={Basket}></Route>
          <Route path='/order-list' element={OrderList}></Route>
          <Route path='/register' element={Registration}></Route>
          <Route path='/sign-in' element={SignIn}></Route>
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}

export default App;
