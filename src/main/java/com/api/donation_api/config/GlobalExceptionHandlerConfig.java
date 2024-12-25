package com.api.donation_api.config;

import com.api.donation_api.dto.ErrorResponseDTO;
import com.api.donation_api.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.security.SignatureException;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandlerConfig {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseDTO.builder()
                        .error("ERRO_DESCONHECIDO")
                        .message(exception.getMessage())
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build()
                );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(ExpiredJwtException ignored) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponseDTO.builder()
                        .error("TOKEN_EXPIRADO")
                        .message("O token informado expirou")
                        .statusCode(HttpStatus.FORBIDDEN.value())
                        .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(BadCredentialsException ignored) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseDTO.builder()
                        .error("CREDENCIAIS_INVALIDAS")
                        .message("Credenciais inválidas")
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .build()
                );
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(SignatureException ignored) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.builder()
                        .error("TOKEN_EXPIRADO")
                        .message("O token informado é inválido")
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build()
                );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoResourceFoundException(ResourceNotFoundException ignored) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponseDTO.builder()
                                .error("RECURSO_NAO_ENCONTRADO")
                                .message("O recurso não foi encontrado")
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .build()
                );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoResourceFoundException(NoHandlerFoundException ignored) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponseDTO.builder()
                                .error("ROTA_NAO_ENCONTRADA")
                                .message("A rota informada não pôde ser encontrada")
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        String mensagemErro = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.builder()
                        .error("ERRO_VALIDACAO")
                        .message(mensagemErro)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build());
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ErrorResponseDTO> handleAutenticacaoException(InvalidLoginException exception){
        String mensagemErro = Objects.requireNonNull(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseDTO.builder()
                        .error("ERRO_AUTENTICACAO")
                        .message(mensagemErro)
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .build());
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<ErrorResponseDTO> handleCpfException(InvalidCpfException exception){
        String mensagemErro = Objects.requireNonNull(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponseDTO.builder()
                        .error("CPF_INVALIDO")
                        .message(mensagemErro)
                        .statusCode(HttpStatus.FORBIDDEN.value())
                        .build());
    }

    @ExceptionHandler(InvalidPostalCodeException.class)
    public ResponseEntity<ErrorResponseDTO> handleCepException(InvalidPostalCodeException exception){
        String mensagemErro = Objects.requireNonNull(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponseDTO.builder()
                        .error("CEP_INVALIDO")
                        .message(mensagemErro)
                        .statusCode(HttpStatus.FORBIDDEN.value())
                        .build());
    }

    @ExceptionHandler(InvalidResourceException.class)
    public ResponseEntity<ErrorResponseDTO> handleCepException(InvalidResourceException exception){
        String mensagemErro = Objects.requireNonNull(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponseDTO.builder()
                        .error("RECURSO_INVALIDO")
                        .message(mensagemErro)
                        .statusCode(HttpStatus.FORBIDDEN.value())
                        .build());
    }
}