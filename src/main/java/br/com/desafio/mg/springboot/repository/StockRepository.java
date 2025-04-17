package br.com.desafio.mg.springboot.repository;

import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.model.StockModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockRepository extends JpaRepository<StockModel, Long> {

    Optional<StockModel> findById(Long id);
}
