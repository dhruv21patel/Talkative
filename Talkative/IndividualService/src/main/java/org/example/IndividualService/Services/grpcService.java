package org.example.IndividualService.Services;

import io.grpc.stub.StreamObserver;
import org.example.IndividualService.IndividualMessageServiceGrpc;
import org.example.IndividualService.MessageRequest;
import org.example.IndividualService.ResponseMessage;
import org.springframework.stereotype.Service;

@Service
public class grpcService extends IndividualMessageServiceGrpc.IndividualMessageServiceImplBase {

    @Override
    public void getMessages(MessageRequest request, StreamObserver<ResponseMessage> responseObserver) {

    }
}
