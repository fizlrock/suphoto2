package dev.fizlrock.suphoto.repositories.exception;

import java.util.List;
import java.util.stream.Collectors;


import org.openapitools.model.WrongField;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.fizlrock.suphoto.domain.exception.EventNotFoundException;
import dev.fizlrock.suphoto.domain.exception.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class DefaultAdvice {


  @ExceptionHandler({ UserNotFoundException.class, EventNotFoundException.class })
  public ResponseEntity<String> handleNotFoundException(Exception e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  ResponseEntity<List<WrongField>> handleValidationException(ConstraintViolationException e) {

    var errors = e.getConstraintViolations();

    var constraints = errors.stream()
        .map(v -> {
          var wf = new WrongField();
          wf.setErrorDescripion(v.getMessage());
          wf.setFieldName(v.getPropertyPath().toString());

          return wf;
        })
        .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(constraints);
  }

}
