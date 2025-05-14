package br.com.desafio.mg.springboot.controller;

import br.com.desafio.mg.springboot.dto.request.AuthRequest;
import br.com.desafio.mg.springboot.dto.response.AuthResponse;
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
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        log.info("Tentativa de login para o usuário: {}", request.getUsername());

        var auth = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        try {
            Authentication authentication = authManager.authenticate(auth);

            if (authentication.isAuthenticated()) {
                log.info("Usuário {} autenticado com sucesso.", request.getUsername());
                String token = jwtTokenUtil.generateToken(authentication.getName());
                return ResponseEntity.ok(new AuthResponse(token));
            } else {
                log.warn("Falha na autenticação para o usuário: {}. Detalhes: {}", request.getUsername(), authentication);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
            }
        } catch (BadCredentialsException e) {
            log.warn("Falha na autenticação para o usuário: {}. Credenciais inválidas.", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        } catch (AuthenticationException e) {
            log.error("Erro durante a autenticação para o usuário: {}", request.getUsername(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro na autenticação.");
        }
    }
}
