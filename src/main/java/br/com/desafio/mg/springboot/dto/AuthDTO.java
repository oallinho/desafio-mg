package br.com.desafio.mg.springboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {
    private String username;
    private String password;
    private String token;

    public AuthDTO() {
    }

    public AuthDTO(String token) {
        this.token = token;
    }
}
