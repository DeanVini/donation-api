package com.api.donation_api.utils;

import com.api.donation_api.dto.RespostaMensagemDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

public class ConstrutorResposta {
    public static ResponseEntity<Object> respostaOk(Object dados){
        if (dados == null || (dados instanceof Map && ((Map<?, ?>) dados).isEmpty())) {
            return ResponseEntity.ok().body(Collections.emptyMap());
        }
        return ResponseEntity.ok(dados);
    }

    public static ResponseEntity<Object> respostaCreated(Object dados) {
        if (dados == null || (dados instanceof Map && ((Map<?, ?>) dados).isEmpty())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Collections.emptyMap());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(dados);
    }

    public static ResponseEntity<Object> respostaSuccess(String mensagem) {
        RespostaMensagemDto resposta = RespostaMensagemDto.builder()
                .mensagem(mensagem)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
