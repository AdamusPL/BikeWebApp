import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Layout } from './components/Layout';
import Home from './pages/Home.jsx';
import BikeShop from './pages/BikeShop.jsx';
import PartShop from './pages/PartShop.jsx';
import BikeDetails from './pages/BikeDetails.jsx';
import PartDetails from './pages/PartDetails.jsx';
import Cart from './pages/Cart.jsx';
import OrderList from './pages/OrderList.jsx';
import Registration from './pages/Registration.jsx';
import SignIn from './pages/SignIn.jsx';
import Account from './pages/Account.jsx';

function App() {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path='/' element={<Home />}></Route>
          <Route path='/account' element={<Account />}></Route>
          <Route path='/bike-shop' element={<BikeShop />}></Route>
          <Route path='/part-shop' element={<PartShop />}></Route>
          <Route path='/bike-details' element={<BikeDetails />}></Route>
          <Route path='/part-details' element={<PartDetails />}></Route>
          <Route path='/cart' element={<Cart />}></Route>
          <Route path='/order-list' element={<OrderList />}></Route>
          <Route path='/register' element={<Registration />}></Route>
          <Route path='/sign-in' element={<SignIn />}></Route>
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}

export default App;
