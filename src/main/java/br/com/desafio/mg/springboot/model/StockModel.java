package br.com.desafio.mg.springboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@RequiredArgsConstructor
@Table(name = "stock")
public class StockModel {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;


    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "drink_id", referencedColumnName = "id")
    private DrinkModel drink;

    @ManyToOne(optional = false)
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private SectionModel section;
}
