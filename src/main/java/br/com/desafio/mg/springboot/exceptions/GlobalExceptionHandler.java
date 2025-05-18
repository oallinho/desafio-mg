package br.com.desafio.mg.springboot.exceptions;

import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.exceptions.drink.DivergentDrinkTypeException;
import br.com.desafio.mg.springboot.exceptions.drink.DrinkAlreadySoldException;
import br.com.desafio.mg.springboot.exceptions.drink.DrinkNotFoundException;
import br.com.desafio.mg.springboot.exceptions.section.MaximumSectionsException;
import br.com.desafio.mg.springboot.exceptions.section.SectionCapacityExceededException;
import br.com.desafio.mg.springboot.exceptions.section.SectionNotFoundException;
import br.com.desafio.mg.springboot.exceptions.stock.StockNotFoundException;
import br.com.desafio.mg.springboot.exceptions.transaction.DrinkAlreadyInSectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({StockNotFoundException.class, SectionNotFoundException.class, DrinkNotFoundException.class})
    public ResponseEntity<ErrorDetails> handleNotFound(RuntimeException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            DivergentDrinkTypeException.class,
            MaximumSectionsException.class,
            SectionCapacityExceededException.class,
            DrinkAlreadySoldException.class,
            DrinkAlreadyInSectionException.class})
    public ResponseEntity<ErrorDetails> handleBadRequest(Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message;

        if (ex instanceof MethodArgumentNotValidException validationException) {
            List<String> errors = validationException.getBindingResult().getFieldErrors()
                    .stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());

            message = "Validation failed: " + String.join("; ", errors);

        } else if (ex instanceof HttpMessageNotReadableException) {
            List<String> validTypes = Arrays.stream(DrinkType.values()).map(Enum::name).toList();

            message = "Invalid drink type provided. Valid types are: " + String.join(", ", validTypes);
        } else {
            message = ex.getMessage();
        }

        return buildErrorResponse(status, message);
    }

    private ResponseEntity<ErrorDetails> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(buildError(status, message));
    }

    private ErrorDetails buildError(HttpStatus status, String message) {
        return new ErrorDetails(status.value(), message, System.currentTimeMillis());
    }
}

