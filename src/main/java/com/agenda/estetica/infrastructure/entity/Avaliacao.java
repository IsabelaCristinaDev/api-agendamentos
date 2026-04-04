package com.agenda.estetica.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="avaliacao")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Integer nota;

    @Column (columnDefinition = "TEXT")
    private String comentario;

    @OneToOne
    @JoinColumn(name= "agendamento_id", nullable = false , unique = true)
    private Agendamento agendamento;

    @Column(nullable = false)
    private LocalDateTime dataAvaliacao = LocalDateTime.now();

}

