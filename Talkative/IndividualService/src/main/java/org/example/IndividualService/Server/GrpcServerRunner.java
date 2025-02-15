package org.example.IndividualService.Server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.example.IndividualService.IndividualMessageServiceGrpc;
import org.example.IndividualService.Services.grpcService;
import org.springframework.stereotype.Component;

@Component
public class GrpcServerRunner {

    private Server server;

    @PostConstruct
    public void start() throws IOException {
        // Define the gRPC server port, e.g., 9090.
        server = ServerBuilder.forPort(9090)
                .addService(new grpcService()) // Register your gRPC service implementation.
                .build()
                .start();

        System.out.println("gRPC Server started, listening on " + server.getPort());

        // Optionally, add a shutdown hook to gracefully stop the server.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server...");
            GrpcServerRunner.this.stop();
            System.err.println("gRPC server shut down.");
        }));
    }

    @PreDestroy
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }
}
