package com.api.donation_api.controller;

import com.api.donation_api.dto.FamiliaRequestDTO;
import com.api.donation_api.dto.PessoaRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Familia;
import com.api.donation_api.service.FamiliaService;
import com.api.donation_api.utils.ConstrutorResposta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/familia")
public class FamiliaController {
    private final FamiliaService familiaService;

    public FamiliaController(FamiliaService familiaService) {
        this.familiaService = familiaService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAll(){
        List<Familia> familias = familiaService.getAllFamilias();
        return ConstrutorResposta.respostaOk(familias);
    }

    @GetMapping("/{idFamilia}")
    public ResponseEntity<Object> getById(@PathVariable Long idFamilia) throws ResourceNotFoundException {
        Familia familia = familiaService.getFamiliaById(idFamilia);
        return ConstrutorResposta.respostaOk(familia);
    }

    @PostMapping("/")
    public ResponseEntity<Object> cadastrarFamilia(@RequestBody FamiliaRequestDTO novaFamiliaDTO) throws ResourceNotFoundException {
        Familia familiaCriada = familiaService.cadatrarFamilia(novaFamiliaDTO);
        return ConstrutorResposta.respostaCreated(familiaCriada);
    }
//
//    @PutMapping("/{idFamilia}")
//    public ResponseEntity<Object> editarFamilia(Long idFamilia, NovaFamiliaDTO novaFamiliaDTO){
//
//    }
//
//    @DeleteMapping("/{idFamilia}")
//    public ResponseEntity<Object> deletarFamilia(Long idFamilia){
//
//    }

    @PutMapping("/{idFamilia}/pessoas")
    public ResponseEntity<Object> adicionarPessoasFamilia(@PathVariable Long idFamilia, @RequestBody List<PessoaRequestDTO> pessoaRequestDTOS) throws ResourceNotFoundException {
        Familia familia = familiaService.adicionarPessoaFamilia(idFamilia, pessoaRequestDTOS);
        return ConstrutorResposta.respostaOk(familia);
    }
}
