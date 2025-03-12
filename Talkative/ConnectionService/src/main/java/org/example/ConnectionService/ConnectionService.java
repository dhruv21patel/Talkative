package org.example.ConnectionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * Hello world!
 *
 */
@SpringBootApplication
//@ComponentScan(basePackages = {"org.example.ConnectionService.Config.", "other.packages.to.scan"})
public class ConnectionService
{
    public static void main( String[] args )
    {
        SpringApplication.run(ConnectionService.class,args);
    }
}
