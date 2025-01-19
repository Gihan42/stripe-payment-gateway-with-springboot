package com.stripe.paymentGateway.stripe.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.paymentGateway.stripe.dto.ProductRequest;
import com.stripe.paymentGateway.stripe.dto.StripeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.Secretkey}")
    private String secretkey;

    public StripeResponse checkProduct(ProductRequest productRequest) {
        Stripe.apiKey = secretkey;

        // Build Product Data
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(productRequest.getName())
                        .build();

        // Build Price Data
        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(productRequest.getCurrency() != null ? productRequest.getCurrency() : "USD")
                        .setUnitAmount(productRequest.getAmount())
                        .setProductData(productData)
                        .build();

        // Build Line Item
        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(productRequest.getQuantity())
                        .setPriceData(priceData)
                        .build();

        // Build Session Parameters
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:8080/success")
                        .setCancelUrl("http://localhost:8080/cancel")
                        .addLineItem(lineItem)
                        .build();

        // Create Session
        try {
            Session session = Session.create(params);
            return new StripeResponse(
                    "SUCCESS",
                    "Payment session created successfully.",
                    session.getId(),
                    session.getUrl()
            );
        } catch (StripeException e) {
            e.printStackTrace();
            return new StripeResponse(
                    "ERROR",
                    "Failed to create payment session: " + e.getMessage(),
                    null,
                    null
            );
        }
    }
}
