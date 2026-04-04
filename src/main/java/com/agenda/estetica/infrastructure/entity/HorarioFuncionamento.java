package com.agenda.estetica.infrastructure.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="horario_funcionamento")

public class HorarioFuncionamento {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek diaSemana;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable= false)
    private LocalTime horaFim;

    @ManyToOne
    @JoinColumn(name="empresa_id", nullable = false)
    private Empresa empresa;




    
}
