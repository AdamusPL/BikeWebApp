package com.bikeparadise.bikewebapp.controller;

import com.bikeparadise.bikewebapp.dto.cart.CartDto;
import com.bikeparadise.bikewebapp.dto.cart.CartItems;
import com.bikeparadise.bikewebapp.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping("/get-cart-products")
    @ResponseBody
    public CartItems getCartProducts(@RequestBody CartDto cartDto){
        return cartService.getCartProducts(cartDto);
    }
}
