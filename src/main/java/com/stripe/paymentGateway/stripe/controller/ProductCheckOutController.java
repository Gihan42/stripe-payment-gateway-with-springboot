package com.stripe.paymentGateway.stripe.controller;

import com.stripe.paymentGateway.stripe.dto.ProductRequest;
import com.stripe.paymentGateway.stripe.dto.StripeResponse;
import com.stripe.paymentGateway.stripe.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/v1")
public class ProductCheckOutController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkOutProducts(@RequestBody ProductRequest productRequest) {
        System.out.println("awa");
        StripeResponse stripeResponse = stripeService.checkProduct(productRequest);
        return ResponseEntity .status(HttpStatus.OK)
                .body(stripeResponse);
    }
}
