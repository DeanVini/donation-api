package com.api.donation_api.service;

import com.api.donation_api.dto.EnderecoRequestDTO;
import com.api.donation_api.dto.FamiliaRequestDTO;
import com.api.donation_api.dto.PessoaRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Endereco;
import com.api.donation_api.model.Familia;
import com.api.donation_api.model.Pessoa;
import com.api.donation_api.repository.FamiliaRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FamiliaService {
    private final FamiliaRepository familiaRepository;
    private final EnderecoService enderecoService;
    private final PessoaService pessoaService;

    public FamiliaService(FamiliaRepository familiaRepository, EnderecoService enderecoService, PessoaService pessoaService) {
        this.familiaRepository = familiaRepository;
        this.enderecoService = enderecoService;
        this.pessoaService = pessoaService;
    }

    public List<Familia> getAllFamilias(){
        return familiaRepository.findAll();
    }

    public Familia getFamiliaById(Long id) throws ResourceNotFoundException {
        return familiaRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Familia não encontrada"));
    }

    public Familia cadatrarFamilia(FamiliaRequestDTO novaFamiliaDTO) throws ResourceNotFoundException {
        Familia.FamiliaBuilder familiaBuilder = Familia.builder()
                .nome(novaFamiliaDTO.getNome());

        Endereco enderecoFamilia = null;
        Pessoa liderFamilia = null;
        Set<Pessoa> pessoasFamilia = new HashSet<>();

        if (novaFamiliaDTO.getEndereco() != null){
            enderecoFamilia = processarEnderecoFamilia(novaFamiliaDTO.getEndereco());
            familiaBuilder.endereco(enderecoFamilia);
        }

        if (novaFamiliaDTO.getLider() != null){
            liderFamilia = processarLiderFamilia(novaFamiliaDTO.getLider());
            familiaBuilder.lider(liderFamilia);
        }

        if (novaFamiliaDTO.getPessoas() != null){
            pessoasFamilia = processarPessoasFamilia(novaFamiliaDTO.getPessoas(), liderFamilia, enderecoFamilia);
            familiaBuilder.pessoas(pessoasFamilia);
        }

        Familia familia = familiaBuilder.pessoas(pessoasFamilia).build();
        Familia familiaSalva = familiaRepository.save(familia);
        pessoaService.atualizarFamiliaPessoas(pessoasFamilia, familiaSalva);
        return familiaSalva;
    }

    public Familia adicionarPessoaFamilia(Long idFamilia ,List<PessoaRequestDTO> pessoasDto) throws ResourceNotFoundException {
        Familia familia = getFamiliaById(idFamilia);
        Set<Pessoa> pessoaParaAtualizar = processarPessoasFamilia(pessoasDto, null, familia.getEndereco());

        for (Pessoa pessoa : pessoaParaAtualizar) {
            pessoa.setFamilia(familia);
        }

        pessoaService.atualizarFamiliaPessoas(pessoaParaAtualizar, familia);

        return familiaRepository.save(familia);
    }

    private Endereco processarEnderecoFamilia(EnderecoRequestDTO enderecoRequestDTO){
        try {
            return enderecoService.obterOuCriar(enderecoRequestDTO);

        }catch (ResourceNotFoundException e) {
            throw new RuntimeException("Erro ao obter ou criar o endereço da família.", e);
        }
    }

    private Pessoa processarLiderFamilia(PessoaRequestDTO liderDto) {
        try {
            return pessoaService.obterOuCriar(liderDto);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Erro ao obter ou criar o líder da família.", e);
        }
    }

    private Set<Pessoa> processarPessoasFamilia(List<PessoaRequestDTO> dtosPessoas, Pessoa lider, Endereco endereco) {
        Set<Pessoa> pessoasFamilia = dtosPessoas.stream()
                .map(pessoaDto -> {
                    try {
                        return pessoaService.obterOuCriar(pessoaDto);
                    } catch (ResourceNotFoundException e) {
                        throw new RuntimeException("Erro ao obter ou criar uma pessoa da família.", e);
                    }
                })
                .collect(Collectors.toSet());
        if (lider != null) {
            pessoasFamilia.add(lider);
        }
        pessoaService.atualizarEnderecoPessoas(pessoasFamilia, endereco);
        return pessoasFamilia;
    }
}
