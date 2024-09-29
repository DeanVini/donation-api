package com.api.donation_api.config;

import com.api.donation_api.dto.RespostaErro;
import com.api.donation_api.exception.ResourceNotFoundException;
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
    public ResponseEntity<RespostaErro> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RespostaErro.builder()
                        .erro("ERRO_DESCONHECIDO")
                        .mensagem(exception.getMessage())
                        .codigoStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build()
                );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<RespostaErro> handleException(ExpiredJwtException ignored) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(RespostaErro.builder()
                        .erro("TOKEN_EXPIRADO")
                        .mensagem("O token informado expirou")
                        .codigoStatus(HttpStatus.FORBIDDEN.value())
                        .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RespostaErro> handleException(BadCredentialsException ignored) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(RespostaErro.builder()
                        .erro("CREDENCIAIS_INVALIDAS")
                        .mensagem("Credenciais inválidas")
                        .codigoStatus(HttpStatus.UNAUTHORIZED.value())
                        .build()
                );
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<RespostaErro> handleException(SignatureException ignored) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RespostaErro.builder()
                        .erro("TOKEN_EXPIRADO")
                        .mensagem("O token informado é inválido")
                        .codigoStatus(HttpStatus.BAD_REQUEST.value())
                        .build()
                );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RespostaErro> handleNoResourceFoundException(ResourceNotFoundException ignored) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        RespostaErro.builder()
                                .erro("RECURSO_NAO_ENCONTRADO")
                                .mensagem("O recurso não foi encontrado")
                                .codigoStatus(HttpStatus.NOT_FOUND.value())
                                .build()
                );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<RespostaErro> handleNoResourceFoundException(NoHandlerFoundException ignored) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        RespostaErro.builder()
                                .erro("ROTA_NAO_ENCONTRADA")
                                .mensagem("A rota informada não pôde ser encontrada")
                                .codigoStatus(HttpStatus.NOT_FOUND.value())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaErro> handleValidationException(MethodArgumentNotValidException ex) {
        String mensagemErro = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RespostaErro.builder()
                        .erro("ERRO_VALIDACAO")
                        .mensagem(mensagemErro)
                        .codigoStatus(HttpStatus.BAD_REQUEST.value())
                        .build());
    }
}