package br.com.desafio.mg.springboot.model;

import br.com.desafio.mg.springboot.enums.DrinkStatus;
import br.com.desafio.mg.springboot.enums.DrinkType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DrinkStatus status;


    @Column(nullable = false)
    private Double volume;

    @Column(nullable = false)
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private SectionModel section;

}
