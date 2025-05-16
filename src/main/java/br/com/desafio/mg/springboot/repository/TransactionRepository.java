package br.com.desafio.mg.springboot.repository;

import br.com.desafio.mg.springboot.enums.TransactionType;
import br.com.desafio.mg.springboot.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

    @Query("SELECT t FROM TransactionModel t WHERE " +
            "(COALESCE(:type, t.type) = t.type) AND " +
            "(:responsible IS NULL OR t.responsible = :responsible)")
    List<TransactionModel> findWithFiltersWithoutDates(
            @Param("type") TransactionType type,
            @Param("responsible") String responsible);
}
