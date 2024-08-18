package com.desafio.estudanteapi.repository;

import com.desafio.estudanteapi.model.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EstudanteRepository extends JpaRepository<Estudante, UUID> {
}
