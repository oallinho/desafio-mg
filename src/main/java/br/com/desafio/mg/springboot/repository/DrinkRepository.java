package br.com.desafio.mg.springboot.repository;

import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.model.DrinkModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DrinkRepository extends JpaRepository<DrinkModel, Long> {
    List<DrinkModel> findByType(DrinkType type);

    List<DrinkModel> findBySectionId(Long drink_id);


}
