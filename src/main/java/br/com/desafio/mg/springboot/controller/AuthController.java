package br.com.desafio.mg.springboot.controller;

import br.com.desafio.mg.springboot.dto.AuthDTO;
import br.com.desafio.mg.springboot.security.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authManager, JwtTokenUtil jwtTokenUtil) {
        this.authManager = authManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO request) {
        log.info("Login attempt for user: {}", request.getUsername());

        var auth = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        try {
            Authentication authentication = authManager.authenticate(auth);

            if (authentication.isAuthenticated()) {
                log.info("User {} successfully authenticated.", request.getUsername());
                String token = jwtTokenUtil.generateToken(authentication.getName());
                return ResponseEntity.ok(new AuthDTO(token));
            } else {
                log.warn("Authentication failed for user: {}. Details: {}", request.getUsername(), authentication);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
            }
        } catch (BadCredentialsException e) {
            log.warn("Authentication failed for user: {}. Invalid credentials.", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        } catch (AuthenticationException e) {
            log.error("Error during authentication for user: {}", request.getUsername(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication error.");
        }
    }
}
