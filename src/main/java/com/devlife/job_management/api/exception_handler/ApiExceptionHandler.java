package com.devlife.job_management.api.exception_handler;

import com.devlife.job_management.domain.exception.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(UserAlreadyExistsException.class)
    protected ResponseEntity<Object> handleDomainException(UserAlreadyExistsException ex, WebRequest request) {


        String uri = request.getDescription(false);
        log.info("URI: {}", uri);
        if (uri.startsWith("uri=")) {
            uri = uri.substring(4);
        }

        HttpStatusCode status = HttpStatus.BAD_REQUEST;
        OffsetDateTime datetime = OffsetDateTime.now();
        String errorMessage = ex.getMessage();
        String title = "Um ou mais erros ocorreram!";

        FieldErrors fieldErrors = new FieldErrors(uri, errorMessage);

        List<FieldErrors> fieldErrorsList = new ArrayList<>();
        fieldErrorsList.add(fieldErrors);

        DefaultError defaultError = new DefaultError(status.value(), datetime, title, fieldErrorsList);


        return handleExceptionInternal(ex, defaultError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthException(AuthenticationException ex, WebRequest request) {


        String uri = request.getDescription(false);

        if (uri.startsWith("uri=")) {
            uri = uri.substring(4);
        }

        HttpStatusCode status = HttpStatus.UNAUTHORIZED;
        OffsetDateTime datetime = OffsetDateTime.now();
        String errorMessage = ex.getMessage();
        String title = "Acesso não autorizado";

        FieldErrors fieldErrors = new FieldErrors(uri, errorMessage);

        List<FieldErrors> fieldErrorsList = new ArrayList<>();
        fieldErrorsList.add(fieldErrors);

        DefaultError defaultError = new DefaultError(status.value(), datetime, title, fieldErrorsList);


        return handleExceptionInternal(ex, defaultError, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, HttpStatusCode status, @NonNull WebRequest request) {
        List<FieldErrors> fieldErrorsList = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String errorName = error.getField();
            String errorMessage = messageSource.getMessage(error, request.getLocale());
            FieldErrors fieldErrors = new FieldErrors(errorName, errorMessage);
            fieldErrorsList.add(fieldErrors);
        });

        OffsetDateTime datetime = OffsetDateTime.now();
        String title = "Um ou mais campos estão incorretos";

        DefaultError defaultError = new DefaultError(status.value(), datetime, title, fieldErrorsList);

        return super.handleExceptionInternal(ex, defaultError, headers, status, request);
    }


}
