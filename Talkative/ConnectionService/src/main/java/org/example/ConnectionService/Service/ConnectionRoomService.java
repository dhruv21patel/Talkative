package org.example.ConnectionService.Service;

import org.example.ConnectionService.DTO.ChatRoom;
import org.example.ConnectionService.DTO.RequestConnection;
import org.example.ConnectionService.Models.SessionTable;
import org.example.ConnectionService.Repository.SessionTableMongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalTime;

@Service
public class ConnectionRoomService {

    @Autowired
    RoomMapper roomMapper;

    @Autowired
    SessionService sessionService;

    @Autowired
    SessionTableMongoRepo mongorepo;

    public Mono<ChatRoom> CreateRoom(RequestConnection requestConnection)
    {
        return roomMapper.GenerateChatId(requestConnection)
                .flatMap(Id -> roomMapper
                        .getRoomid(Id)
                        .flatMap(RoomId -> roomMapper
                                .Save(Id,RoomId)
                                .flatMap(Status -> {
                                    if(Status) return Mono.just(RoomId);
                                    throw new RuntimeException("Failed to Store The Mapping");
                                }).onErrorMap(e -> new RuntimeException("Failed Saving The Mapping Because of Server Error"))
                )).doOnSuccess(System.out::println);
    }

    public Mono<SessionTable> CreateUserSession(String Roomid,String Userid)
    {
        return sessionService.GetSession(Userid)
                .flatMap(CurrentSession  ->  {
                    CurrentSession.setRoomID(Roomid);
                        return sessionService.SaveSession(CurrentSession,Userid).flatMap(Stat -> {
                            if(Stat)
                            {
                                return Mono.just(CurrentSession);
                            }else {
                                Mono.error(new RuntimeException("REDIS Server Error"));
                            }
                            return null;
                        }
                        ).doOnError(e -> Mono.error(new RuntimeException("REDIS Server Error"+ e)));
                        }
                ).flatMap(CurrSession  -> mongorepo.save(CurrSession).doOnError(e -> Mono.error(new RuntimeException("Mongo Server Error"+ e))))
                .switchIfEmpty(
                       mongorepo.findById(Long.valueOf(Userid)).switchIfEmpty(
                               mongorepo.save( SessionTable.builder().userID(Long.valueOf(Userid)).roomID(Roomid).is_Active(true).lastSeen(LocalTime.now()).build() )
                                       .flatMap( Session -> sessionService.SaveSession(Session,Userid)
                                               .flatMap(Status -> {
                                                   if(Status)
                                                   {
                                                       return Mono.just(Session);
                                                   }else {
                                                   Mono.error(new RuntimeException("Redis Server Error"));
                                                   }
                                                   return null;
                                               })).doOnError(e -> Mono.error(new RuntimeException("Mongo Server Error"+ e)) )
                       ).flatMap(OldSession -> {
                           OldSession.setRoomID(Roomid);
                            return mongorepo.save(OldSession).flatMap(Session -> sessionService.SaveSession(OldSession,Userid).flatMap(Status -> {
                                                   if(Status)
                                                   {
                                                       return Mono.just(Session);
                                                   }else {
                                                   Mono.error(new RuntimeException("Redis Server Error"));
                                                   }
                                                   return null;
                                               }));
                       })
                );
    }

    public Mono<String> DeleteUserSession(RequestConnection requestConnection,String roomID)
    {

        return sessionService.DeleteSession(requestConnection.getSender_id().toString()).flatMap(status -> {
            if(status)
            {
                mongorepo.deleteById(requestConnection.getSender_id()).doOnSuccess(Stat -> Mono.just("Successfull"+Stat));
            }else {
                return Mono.error(new RuntimeException("Failed"));
            }
            return Mono.just("Successfull");
        });
    }
}
