package com.desafio.estudanteapi.service;

import com.desafio.estudanteapi.model.Estudante;
import com.desafio.estudanteapi.repository.EstudanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EstudanteService {

    @Autowired
    private EstudanteRepository estudanteRepository;

    public Estudante criarEstudante(Estudante estudante) {
        return estudanteRepository.save(estudante);
    }

    public Optional<Estudante> buscarEstudantePorId(UUID id) {
        return estudanteRepository.findById(id);
    }

    public List<Estudante> listarTodosEstudantes() {
        return estudanteRepository.findAll();
    }

    public Estudante atualizarEstudante(UUID id, Estudante estudanteAtualizado) {
        return estudanteRepository.findById(id).map(estudante -> {
            estudante.setNome(estudanteAtualizado.getNome());
            estudante.setEmail(estudanteAtualizado.getEmail());
            estudante.setDataNascimento(estudanteAtualizado.getDataNascimento());
            estudante.setMatricula(estudanteAtualizado.getMatricula());
            return estudanteRepository.save(estudante);
        }).orElseThrow(() -> new RuntimeException("Estudante não encontrado!"));
    }

    public void deletarEstudante(UUID id) {
        estudanteRepository.deleteById(id);
    }
}
