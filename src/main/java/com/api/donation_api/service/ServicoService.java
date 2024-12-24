package com.api.donation_api.service;

import com.api.donation_api.dto.ServicoRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Service;
import com.api.donation_api.repository.ServiceRepository;
import com.api.donation_api.utils.CustomBeanUtils;

import java.util.List;

@org.springframework.stereotype.Service
public class ServicoService {
    private final ServiceRepository serviceRepository;

    public ServicoService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<Service> getAllServicos(){
        return serviceRepository.findAll();
    }

    public List<Service> getAllServicosAtivos(){
        return serviceRepository.findAllByAvailableTrue();
    }

    public Service getById(Long idServico) throws ResourceNotFoundException {
        return serviceRepository.findById(idServico)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível encontrar o serviço informado."));
    }

    public Service cadastarServico(ServicoRequestDTO servicoRequestDTO){
        Service service = Service.builder()
                .type(servicoRequestDTO.getTipo())
                .description(servicoRequestDTO.getDescricao())
                .build();

        return serviceRepository.save(service);
    }

    public void atualizarServico(Long idServico, ServicoRequestDTO servicoRequestDTO) throws ResourceNotFoundException {
        Service service = serviceRepository.findById(idServico)
                .orElseThrow(()->new ResourceNotFoundException("Não foi possível encontrar o serviço!"));

        CustomBeanUtils.copyNonNullProperties(servicoRequestDTO, service);
        serviceRepository.save(service);
    }

}
