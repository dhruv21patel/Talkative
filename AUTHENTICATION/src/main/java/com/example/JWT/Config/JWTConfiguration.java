package com.example.JWT.Config;

import com.example.JWT.Service.UserService.Getuser;
import com.example.JWT.Service.JwtService.JwtDbImpl;
import com.example.JWT.Service.JwtService.JwtGenerationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JWTConfiguration extends OncePerRequestFilter {


    @Autowired
    JwtGenerationService jwtservice;

    @Autowired
    JwtDbImpl jwtdb;

    // this is bean extracted provided by spring boot which is used to get the beans
    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String Authheader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(Authheader != null && Authheader.startsWith("Bearer ")) {
            token = Authheader.substring(7);
            username = jwtservice.Extractusername(token);
            System.out.println("----------------------------------------------------------" + username);


            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userdetails = context.getBean(Getuser.class).loadUserByUsername(username);

                if (jwtservice.verifyToken(token, userdetails.getUsername()) && jwtdb.validateToken(token)) {
                    UsernamePasswordAuthenticationToken Authtoken = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
                    Authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(Authtoken);
                    response.sendRedirect("/data");
                    return;
                }
            }
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            response.sendRedirect("/data"); // Redirect unauthorized users to login
            response.setStatus(200);
            return;
        }
        filterChain.doFilter(request,response);
    }
}
