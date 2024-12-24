package com.api.donation_api.controller;

import com.api.donation_api.dto.FamiliaRequestDTO;
import com.api.donation_api.dto.PessoaRequestDTO;
import com.api.donation_api.dto.ServicoRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Family;
import com.api.donation_api.service.FamiliaService;
import com.api.donation_api.utils.ResponseConstructorUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/familia")
public class FamiliaController {
    private final FamiliaService familiaService;

    public FamiliaController(FamiliaService familiaService) {
        this.familiaService = familiaService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAll(){
        List<Family> familias = familiaService.getAllFamilias();
        return ResponseConstructorUtils.okResponse(familias);
    }

    @GetMapping("/{idFamilia}")
    public ResponseEntity<Object> getById(@PathVariable Long idFamilia) throws ResourceNotFoundException {
        Family familia = familiaService.getFamiliaById(idFamilia);
        return ResponseConstructorUtils.okResponse(familia);
    }

    @PostMapping("/")
    public ResponseEntity<Object> cadastrarFamilia(@RequestBody FamiliaRequestDTO novaFamiliaDTO) throws ResourceNotFoundException {
        Family familiaCriada = familiaService.cadatrarFamilia(novaFamiliaDTO);
        return ResponseConstructorUtils.createdResponse(familiaCriada);
    }

    @PutMapping("/{idFamilia}/pessoas")
    public ResponseEntity<Object> adicionarPessoasFamilia(@PathVariable Long idFamilia, @RequestBody List<PessoaRequestDTO> pessoaRequestDTOS) throws ResourceNotFoundException {
        Family familia = familiaService.adicionarPessoaFamilia(idFamilia, pessoaRequestDTOS);
        return ResponseConstructorUtils.okResponse(familia);
    }

    @PutMapping("/{idFamilia}/servicos")
    public ResponseEntity<Object> adicionarServicoFamilia(@PathVariable Long idFamilia, @RequestBody ServicoRequestDTO servicoRequestDTO) throws ResourceNotFoundException {
        Family familia = familiaService.vincularServicoFamilia(idFamilia, servicoRequestDTO);
        return ResponseConstructorUtils.okResponse(familia);
    }
}
