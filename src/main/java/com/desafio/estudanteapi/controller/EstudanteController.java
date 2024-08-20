package com.desafio.estudanteapi.controller;

import com.desafio.estudanteapi.model.Estudante;
import com.desafio.estudanteapi.service.EstudanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/estudantes")
@Tag(name = "Estudante", description = "API para gerenciamento de estudantes")
public class EstudanteController {

    @Autowired
    private EstudanteService estudanteService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudante criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos!")
    })
    @Operation(summary = "Criar um novo estudante")
    public ResponseEntity<Estudante> criarEstudante(@RequestBody Estudante estudante) {
        Estudante novoEstudante = estudanteService.criarEstudante(estudante);
        return new ResponseEntity<>(novoEstudante, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudante encontrado!"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado!")
    })
    @Operation(summary = "Buscar um estudante pelo ID")
    public ResponseEntity<Estudante> buscarEstudantePorId(@PathVariable UUID id) {
        Optional<Estudante> estudante = estudanteService.buscarEstudantePorId(id);
        return estudante.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estudantes obtida com sucesso!")
    })
    @Operation(summary = "Listar todos os estudantes")
    public List<Estudante> listarTodosEstudantes() {
        return estudanteService.listarTodosEstudantes();
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudante atualizado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado!")
    })
    @Operation(summary = "Atualizar as informações de um estudante")
    public ResponseEntity<Estudante> atualizarEstudante(@PathVariable UUID id, @RequestBody Estudante estudante) {
        try {
            Estudante estudanteAtualizado = estudanteService.atualizarEstudante(id, estudante);
            return ResponseEntity.ok(estudanteAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estudante removido com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado!")
    })
    @Operation(summary = "Remover um estudante pelo ID")
    public ResponseEntity<Void> deletarEstudante(@PathVariable UUID id) {
        estudanteService.deletarEstudante(id);
        return ResponseEntity.noContent().build();
    }
}
