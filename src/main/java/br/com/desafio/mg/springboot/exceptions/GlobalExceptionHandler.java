package br.com.desafio.mg.springboot.exceptions;

import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.exceptions.drink.DivergentDrinkTypeException;
import br.com.desafio.mg.springboot.exceptions.drink.DrinkAlreadySoldException;
import br.com.desafio.mg.springboot.exceptions.drink.DrinkNotFoundException;
import br.com.desafio.mg.springboot.exceptions.section.MaximumSectionsException;
import br.com.desafio.mg.springboot.exceptions.section.SectionCapacityExceededException;
import br.com.desafio.mg.springboot.exceptions.section.SectionNotFoundException;
import br.com.desafio.mg.springboot.exceptions.stock.StockNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorDetails buildError(HttpStatus status, String message) {
        return new ErrorDetails(status.value(), message, System.currentTimeMillis());
    }

    @ExceptionHandler({StockNotFoundException.class, SectionNotFoundException.class, DrinkNotFoundException.class})
    public ResponseEntity<ErrorDetails> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationErrors(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(HttpStatus.BAD_REQUEST, "Validation failed: " + ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleEnumConversionError(HttpMessageNotReadableException ex) {
        List<String> validTypes = Arrays.stream(DrinkType.values()).map(Enum::name).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(HttpStatus.BAD_REQUEST, "Invalid drink type provided. Valid types are: " + validTypes));
    }

    @ExceptionHandler(DivergentDrinkTypeException.class)
    public ResponseEntity<ErrorDetails> handleDivergentDrinkType(DivergentDrinkTypeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
    @ExceptionHandler(MaximumSectionsException.class)
    public ResponseEntity<ErrorDetails> handleMaximumSections(MaximumSectionsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(SectionCapacityExceededException.class)
    public ResponseEntity<ErrorDetails> handleSectionCapacityExceeded(SectionCapacityExceededException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(DrinkAlreadySoldException.class)
    public ResponseEntity<ErrorDetails> handleSectionCapacityExceeded(DrinkAlreadySoldException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
}

