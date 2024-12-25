package com.api.donation_api.controller;

import com.api.donation_api.dto.ServiceRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Service;
import com.api.donation_api.service.ServicoService;
import com.api.donation_api.utils.ResponseConstructorUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceController {
    private final ServicoService servicoService;

    public ServiceController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllServices(@RequestParam(value = "inactive", defaultValue = "false") boolean inactive) {
        List<Service> services = inactive ? servicoService.getAllServicos()
                : servicoService.getAllServicosAtivos();

        return ResponseConstructorUtils.okResponse(services);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createService(@RequestBody ServiceRequestDTO serviceRequestDTO){
        Service createdService = servicoService.cadastarServico(serviceRequestDTO);
        return ResponseConstructorUtils.okResponse(createdService);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<Object> updateService(@PathVariable Long serviceId, @RequestBody ServiceRequestDTO serviceRequestDTO) throws ResourceNotFoundException {
        servicoService.atualizarServico(serviceId, serviceRequestDTO);
        return ResponseConstructorUtils.successResponse("Serviço atualizado com sucesso!");
    }
}
