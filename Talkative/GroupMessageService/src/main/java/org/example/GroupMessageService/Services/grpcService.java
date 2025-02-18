package org.example.GroupMessageService.Services;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.GroupMessageService.GroupMessageRequest;
import org.example.GroupMessageService.GroupMessageServiceGrpc;
import org.example.GroupMessageService.GroupResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

@GrpcService
public class grpcService extends GroupMessageServiceGrpc.GroupMessageServiceImplBase {

    @Autowired
    ChatService chatService;
    @Override
    public void getGroupMessages(GroupMessageRequest request, StreamObserver<GroupResponseMessage> responseObserver) {

        chatService.getMessagesByChatId(request.getChatId()).map(M -> GroupResponseMessage.newBuilder()
                .setMessage(M.getMessage())
                .setSeen(M.getSeen())
                .setSenderId(M.getSenderId())
                .setChatName(M.getChatId())
                .setSendTime(Timestamp.valueOf(M.getSendTime()))
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
