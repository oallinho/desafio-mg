package br.com.desafio.mg.springboot.model;

import br.com.desafio.mg.springboot.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@RequiredArgsConstructor
@Table(name = "transaction")
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private Double volume;

    @Column(nullable = false)
    private String responsible;

    private String message;

    @ManyToOne(optional = false)
    @JoinColumn(name = "drink_id", referencedColumnName = "id")
    private DrinkModel drink;

    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private SectionModel section;

    @ManyToOne
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    private StockModel stock;


}

