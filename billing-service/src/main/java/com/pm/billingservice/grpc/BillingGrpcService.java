package com.pm.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest,
            StreamObserver<BillingResponse> responseObserver) {

        log.info("createBillingAccount request received {}" , billingRequest.toString());

        //Business logic to create billing account would go here
        //like save to database, perform calculations, etc

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("12345") // This would be generated or retrieved from DB
                .setStatus("ACTIVE")
                .build();

        responseObserver.onNext(response);
        log.info("createBillingAccount response sent {}" , response.toString());
        // this is different form REST where we return response
        // in gRPC we use responseObserver to send the response
        // WE can send multiple responses if needed
        responseObserver.onCompleted();
        // at the end we call onCompleted to indicate that the RPC is complete


    }
}
