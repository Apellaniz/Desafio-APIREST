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
            @ApiResponse(responseCode = "200", description = "Estudante Criado!"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar estudante!")
    })
    @Operation(summary = "Criar um novo estudante")
    public ResponseEntity<Estudante> criarEstudante(@RequestBody Estudante estudante) {
        Estudante novoEstudante = estudanteService.criarEstudante(estudante);
        return new ResponseEntity<>(novoEstudante, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudante buscado pelo ID!"),
            @ApiResponse(responseCode = "400", description = "Erro ao encontrar estudante pelo ID!")
    })
    @Operation(summary = "Buscar um estudante pelo ID")
    public ResponseEntity<Estudante> buscarEstudantePorId(@PathVariable UUID id) {
        Optional<Estudante> estudante = estudanteService.buscarEstudantePorId(id);
        return estudante.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos os estudantes listados!"),
            @ApiResponse(responseCode = "400", description = "Erro ao listar todos os estudantes!")
    })
    @Operation(summary = "Listar todos os estudantes")
    public List<Estudante> listarTodosEstudantes() {
        return estudanteService.listarTodosEstudantes();
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informação do estudante atualziada!"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar informações do estudante!")
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
            @ApiResponse(responseCode = "200", description = "Estudante removido por ID!"),
            @ApiResponse(responseCode = "400", description = "Erro ao remover estudante pelo ID!")
    })
    @Operation(summary = "Remover um estudante pelo ID")
    public ResponseEntity<Void> deletarEstudante(@PathVariable UUID id) {
        estudanteService.deletarEstudante(id);
        return ResponseEntity.noContent().build();
    }
}
