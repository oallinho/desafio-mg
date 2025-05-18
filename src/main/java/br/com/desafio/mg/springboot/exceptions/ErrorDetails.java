package br.com.desafio.mg.springboot.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {
    private int status;
    private String message;
    private long timestamp;

    public ErrorDetails(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
