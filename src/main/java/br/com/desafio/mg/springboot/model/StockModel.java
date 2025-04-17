package br.com.desafio.mg.springboot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@Entity
@RequiredArgsConstructor
@Table(name = "stock")
public class StockModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int maximumSections;

    @Column(nullable = false)
    private double alcoholicMaximum;

    @Column(nullable = false)
    private double nonAlcoholicMaximum;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;
}
