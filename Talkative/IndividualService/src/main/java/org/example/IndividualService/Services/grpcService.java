package org.example.IndividualService.Services;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.ConnectionService.ConnectionServiceGrpc;
import org.example.ConnectionService.MessageRequest;
import org.example.ConnectionService.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

@GrpcService
public class grpcService extends ConnectionServiceGrpc.ConnectionServiceImplBase {

    @Autowired
    ChatService chatService;

    @Override
    public void getIndividualMessages(MessageRequest request, StreamObserver<ResponseMessage> responseObserver) {
         chatService.getmessages(request.getChatId()).map(M -> {
                     System.out.println("Message data: " + M);
                     return ResponseMessage.newBuilder()
                             .setMessage(M.getMessage())
                             .setSeen(M.getSeen())
                             .setSenderId(M.getSenderID())
                             .setChatName(M.getChatID())
                             .setSendTime(TimestampConverter.toProtoTimestamp(Timestamp.valueOf(M.getSendTime().toLocalDateTime())))
                             .build();
                 }
                ).doOnError(e -> {
                    e.printStackTrace();  // Print stack trace for detailed error info
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

    public static class TimestampConverter {
        public static com.google.protobuf.Timestamp toProtoTimestamp(java.sql.Timestamp sqlTimestamp) {
            return com.google.protobuf.Timestamp.newBuilder()
                    .setSeconds(sqlTimestamp.getTime() / 1000)  // Convert milliseconds to seconds
                    .setNanos((int) (sqlTimestamp.getTime() % 1000) * 1000000)  // Convert the remainder to nanos
                    .build();
        }
    }
}
