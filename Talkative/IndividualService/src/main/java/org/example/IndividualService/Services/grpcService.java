package org.example.IndividualService.Services;

import io.grpc.stub.StreamObserver;
import org.example.IndividualService.IndividualMessageServiceGrpc;
import org.example.IndividualService.MessageRequest;
import org.example.IndividualService.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class grpcService extends IndividualMessageServiceGrpc.IndividualMessageServiceImplBase {

    @Autowired
    ChatService chatService;
    @Override
    public void getIndividualMessages(MessageRequest request, StreamObserver<ResponseMessage> responseObserver) {

        chatService.getmessages(request.getChatId()).map(M -> ResponseMessage.newBuilder()
                .setMessage(M.getMessage())
                .setSeen(M.getSeen())
                .setSenderId(M.getSenderID())
                .setChatName(M.getChatID())
                .setSendTime(M.getSendTime())
                .build()
        ).doOnError(e -> {
            System.out.println("Error while fetching messages: " + e.getMessage());
            responseObserver.onError(e);
        })
        .subscribe(responseObserver::onNext,  // onNext: send each message to the client
            error -> {
                // onError is already handled above, but we can log additional details if needed.
                System.err.println("Subscription error: " + error.getMessage());
            },
                responseObserver::onCompleted  // onComplete: signal the end of the stream
        );

    }
}
