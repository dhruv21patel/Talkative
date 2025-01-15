package org.example.ConnectionService.Controller;


import org.example.ConnectionService.DTO.RequestConnection;
import org.example.ConnectionService.Models.SessionTable;

import org.example.ConnectionService.Service.ConnectionRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;


@RestController
@RequestMapping("/Conn")
public class ConnectoinController {

    @Autowired
    ConnectionRoomService connectionRoomService;


    @PostMapping("/")
    public Mono<SessionTable> GetConnection(@RequestBody RequestConnection requestConnection) throws IOException {

        return  connectionRoomService.CreateRoom(requestConnection).flatMap(
                Room ->
                        connectionRoomService.CreateUserSession( Room.getRoomid(),requestConnection.getSender_id().toString() )
        );
    }

    @PostMapping("/RejectConn/{roomID}")
    public Mono<String> RejectConnection(@PathVariable String roomID, @RequestBody RequestConnection requestConnection)
    {
        return connectionRoomService.DeleteUserSession(requestConnection,roomID);
    }

}
