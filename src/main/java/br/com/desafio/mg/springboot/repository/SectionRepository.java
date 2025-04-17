package br.com.desafio.mg.springboot.repository;

import br.com.desafio.mg.springboot.model.SectionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SectionRepository extends JpaRepository<SectionModel, Long> {

    Optional<SectionModel> findById(Long id);
}
