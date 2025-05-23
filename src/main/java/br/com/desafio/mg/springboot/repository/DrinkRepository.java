package br.com.desafio.mg.springboot.repository;

import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.model.DrinkModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkRepository extends JpaRepository<DrinkModel, Long> {
    List<DrinkModel> findByType(DrinkType type);

    @Query("SELECT d.type, SUM(d.volume) FROM DrinkModel d GROUP BY d.type")
    List<Object[]> getTotalVolumeByType();

    @Query("SELECT d FROM DrinkModel d WHERE d.section.id = :sectionId")
    List<DrinkModel> getDrinksBySectionId(@Param("sectionId") Long sectionId);

    @Query("SELECT d.section.id, SUM(d.volume) FROM DrinkModel d GROUP BY d.section.id")
    List<Object[]> getCurrentVolumesBySection();

    @Query("SELECT SUM(d.volume) FROM DrinkModel d WHERE d.section.id = :sectionId")
    Double getCurrentVolumeBySection(@Param("sectionId") Long sectionId);
}
