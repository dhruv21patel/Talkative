package com.example.JWT.Service.JwtService;

import com.example.JWT.Model.JwtTokens;
import com.example.JWT.Repository.JwtRepo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtDbImpl implements Jwtdatabaseinterface {

    private final JwtRepo jwtrepo;

    public JwtDbImpl(JwtRepo jwtrepo) {
        this.jwtrepo = jwtrepo;
    }

    private Date getdate(Integer expiresAt){
        Date d = new Date();
        long currenttime = d.getTime();
        long futuretime = currenttime+expiresAt;
        Date futuredate =  new Date(futuretime);
        return futuredate;
    }
    public JwtTokens saveToken(String email, String token, Integer expiresAt) {
        //cross-check if the user already has a token with that email
        Optional<JwtTokens> existtoken  =  jwtrepo.findByEmail(email);
        if(existtoken.isPresent())
        {
            jwtrepo.delete(existtoken.get());
        }

        //creating a new token for a user
        JwtTokens jwtToken = new JwtTokens();
        jwtToken.setEmail(email);
        jwtToken.setToken(token);
        jwtToken.setExpiresAt(getdate(expiresAt));
        return jwtrepo.save(jwtToken);
    }

    public boolean validateToken(String token) {
        Optional<JwtTokens> jwtToken = jwtrepo.findByToken(token);
        if (jwtToken.isPresent()) {
            if(jwtToken.get().getExpiresAt().after(new Date()))
            {
                return jwtToken.get().getExpiresAt().after(new Date());
            }
            jwtrepo.delete(jwtToken.get());
        }
        return false;
    }

    public void deleteToken(String token) {
        jwtrepo.findByToken(token).ifPresent(jwtrepo::delete);
    }
}
