package com.examples;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/pay")
public class PaymentController {

    @Inject
    PaymentService paymentService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String pay() {
        return paymentService.processPayment();
    }
}
