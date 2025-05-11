package br.com.desafio.mg.springboot.repository;

import br.com.desafio.mg.springboot.enums.TransactionType;
import br.com.desafio.mg.springboot.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

    @Query("SELECT t FROM TransactionModel t WHERE " +
            "(:type IS NULL OR t.type = :type) AND " +
            "(:responsible IS NULL OR t.responsible = :responsible) AND " +
            "(:start IS NULL OR t.createdAt >= :start) AND " +
            "(:end IS NULL OR t.createdAt <= :end)")
    List<TransactionModel> findWithFilters(@Param("type") TransactionType type,
            @Param("responsible") String responsible,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}
