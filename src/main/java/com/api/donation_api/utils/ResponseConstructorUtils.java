package com.api.donation_api.utils;

import com.api.donation_api.dto.ResponseMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

public class ResponseConstructorUtils {
    public static ResponseEntity<Object> okResponse(Object dados){
        if (dados == null || (dados instanceof Map && ((Map<?, ?>) dados).isEmpty())) {
            return ResponseEntity.ok().body(Collections.emptyMap());
        }
        return ResponseEntity.ok(dados);
    }

    public static ResponseEntity<Object> createdResponse(Object dados) {
        if (dados == null || (dados instanceof Map && ((Map<?, ?>) dados).isEmpty())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Collections.emptyMap());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(dados);
    }

    public static ResponseEntity<Object> successResponse(String mensagem) {
        ResponseMessageDTO resposta = ResponseMessageDTO.builder()
                .message(mensagem)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
