package com.devlife.job_management.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
  private MessageSource messageSource;

  public ExceptionHandlerController(MessageSource message) {
    this.messageSource = message;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    List<ErrorMessageDTO> dto = new ArrayList<>();

    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

    fieldErrors.forEach(error -> {
      String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
      ErrorMessageDTO errorDto = new ErrorMessageDTO(message, error.getField());
      dto.add(errorDto);
    });

    return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
  }
}
