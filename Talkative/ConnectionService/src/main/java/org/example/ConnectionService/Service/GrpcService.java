package org.example.ConnectionService.Service;


import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.ConnectionService.ConnectionServiceGrpc;
import org.example.ConnectionService.MessageRequest;
import org.example.ConnectionService.ResponseMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import io.grpc.stub.StreamObserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GrpcService {

    private static final Logger logger = LoggerFactory.getLogger(GrpcService.class);

    @GrpcClient("grpc-ind")
    ConnectionServiceGrpc.ConnectionServiceStub asyncStub;

//    @GrpcClient("grpc-group")
//    ConnectionServiceGrpc.ConnectionServiceStub asyncStub1;

    public Flux<ResponseMessage> getIndivi(String chatId){

        MessageRequest request = MessageRequest.newBuilder()
                .setChatId(chatId)
                .build();

        System.out.println("Making RPC Call");

        return Flux.create(emitter -> {
            asyncStub.getIndividualMessages(request, new StreamObserver<ResponseMessage>() {
                @Override
                public void onNext(ResponseMessage response) {
                    logger.info("Received individual message: {}");
                    emitter.next(response); // Emit each message to Flux
                }

                @Override
                public void onError(Throwable t) {
                    logger.error("Error during gRPC call", t);
                    t.printStackTrace();
                    emitter.error(t); // Handle errors
                }

                @Override
                public void onCompleted() {
                    logger.info("Completed individual messages stream");
                    emitter.complete(); // Complete when stream ends
                }
            });
        });
    }

    public Flux<ResponseMessage> getGroup(String chatId) {

        MessageRequest request = MessageRequest.newBuilder()
                .setChatId(chatId)
                .build();

        return Flux.create(emitter -> {
            asyncStub.getGroupMessages(request, new StreamObserver<ResponseMessage>() {
                @Override
                public void onNext(ResponseMessage response) {
                    logger.info("Received group message: {}", response);
                    emitter.next(response); // Emit each message
                }

                @Override
                public void onError(Throwable t) {
                    logger.error("Error during gRPC call", t);
                    emitter.error(t); // Handle errors
                }

                @Override
                public void onCompleted() {
                    logger.info("Completed group messages stream");
                    emitter.complete(); // Complete the Flux
                }
            });
        });
    }
}
