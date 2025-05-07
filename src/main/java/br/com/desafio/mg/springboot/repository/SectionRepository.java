package br.com.desafio.mg.springboot.repository;

import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.model.DrinkModel;
import br.com.desafio.mg.springboot.model.SectionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SectionRepository extends JpaRepository<SectionModel, Long> {

    Optional<SectionModel> findById(Long id);

    List<SectionModel> findByStockId(Long stockId);

    @Query("SELECT count(s) FROM SectionModel s WHERE s.stock.id = :stockId")
    int countByStockId(@Param("stockId") Long stockId);

    @Query("""
                SELECT s\s
                FROM SectionModel s\s
                LEFT JOIN DrinkModel d ON s.id = d.section.id\s
                WHERE s.permittedType = :type
                GROUP BY s\s
                HAVING COALESCE(SUM(d.liter), 0) < s.maximumCapacity
            """)
    List<SectionModel> findAvailableSectionsByType(@Param("type") DrinkType type);

    List<SectionModel> findByPermittedType(DrinkType permittedType);

}
