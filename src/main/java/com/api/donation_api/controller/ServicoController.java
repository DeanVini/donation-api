package com.api.donation_api.controller;

import com.api.donation_api.dto.ServicoRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Service;
import com.api.donation_api.service.ServicoService;
import com.api.donation_api.utils.ResponseConstructorUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servico")
public class ServicoController {
    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> listarServicos(@RequestParam(value = "inativos", defaultValue = "false") boolean inativos) {
        List<Service> services = inativos ? servicoService.getAllServicos()
                : servicoService.getAllServicosAtivos();

        return ResponseConstructorUtils.okResponse(services);
    }

    @PostMapping("/")
    public ResponseEntity<Object> cadastrarServico(@RequestBody ServicoRequestDTO servicoRequestDTO){
        Service serviceCriado = servicoService.cadastarServico(servicoRequestDTO);
        return ResponseConstructorUtils.okResponse(serviceCriado);
    }

    @PutMapping("/{idServico}")
    public ResponseEntity<Object> editarServico(@PathVariable Long idServico, @RequestBody ServicoRequestDTO servicoRequestDTO) throws ResourceNotFoundException {
        servicoService.atualizarServico(idServico, servicoRequestDTO);
        return ResponseConstructorUtils.successResponse("Serviço atualizado com sucesso!");
    }
}
