package com.api.donation_api.service;

import com.api.donation_api.dto.ServicoRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Servico;
import com.api.donation_api.repository.ServicoRepository;
import com.api.donation_api.utils.BeanPersonalizadoUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {
    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public List<Servico> getAllServicos(){
        return servicoRepository.findAll();
    }

    public List<Servico> getAllServicosAtivos(){
        return servicoRepository.findAllByDisponivelTrue();
    }

    public Servico getById(Long idServico) throws ResourceNotFoundException {
        return servicoRepository.findById(idServico)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível encontrar o serviço informado."));
    }

    public Servico cadastarServico(ServicoRequestDTO servicoRequestDTO){
        Servico servico = Servico.builder()
                .tipo(servicoRequestDTO.getTipo())
                .descricao(servicoRequestDTO.getDescricao())
                .build();

        return servicoRepository.save(servico);
    }

    public void atualizarServico(Long idServico, ServicoRequestDTO servicoRequestDTO) throws ResourceNotFoundException {
        Servico servico = servicoRepository.findById(idServico)
                .orElseThrow(()->new ResourceNotFoundException("Não foi possível encontrar o serviço!"));

        BeanPersonalizadoUtils.copiarPropriedadesNaoNulas(servicoRequestDTO, servico);
        servicoRepository.save(servico);
    }

}
