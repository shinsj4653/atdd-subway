package kuit.subway.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> dataExceptionHandle() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> sqlExceptionHandle() {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
    }
    @ExceptionHandler(RuntimeException.class)
    public  ResponseEntity<String> runTimeExceptionHandle()  {
        return ResponseEntity.status(NOT_FOUND).build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public  ResponseEntity<String> entityNotFoundExceptionHandle(EntityNotFoundException e)  {

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }



}
