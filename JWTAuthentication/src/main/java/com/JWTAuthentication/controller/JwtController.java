package com.JWTAuthentication.controller;

import com.JWTAuthentication.entity.JwtRequest;
import com.JWTAuthentication.entity.JwtResponse;
import com.JWTAuthentication.helper.JWTUtil;
import com.JWTAuthentication.service.CustomUserDetailsService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;

@RestController
public class JwtController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JWTUtil jwtUtil;


    @RequestMapping(value = "/token" , method= RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest);
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch(UsernameNotFoundException e){
            e.printStackTrace();
            throw new Exception("Bad credentials!");
        } catch (BadCredentialsException e){
            e.printStackTrace();
            throw new Exception("Bad credentials!");
        }

        // fine area..
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println("JWT " + token);

        return ResponseEntity.ok(new JwtResponse(token));
    }

}
