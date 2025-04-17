package br.com.desafio.mg.springboot.model;

import br.com.desafio.mg.springboot.enums.DrinkType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "drink")
public class DrinkModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DrinkType type;

    @Column(nullable = false)
    private Double liter;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private SectionModel section;
}
