package com.agenda.estetica.infrastructure.repository;

import com.agenda.estetica.infrastructure.entity.Agendamento;
import com.agenda.estetica.infrastructure.entity.Cliente;
import com.agenda.estetica.infrastructure.entity.Funcionaria;
import com.agenda.estetica.infrastructure.entity.Servico;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    Agendamento findByFuncionariaAndDataHoraAgendamentoBetween(
            Funcionaria funcionaria,
            LocalDateTime dataHoraInicio,
            LocalDateTime dataHoraFinal
    );

    @Transactional
    void deleteByDataHoraAgendamentoAndCliente(
            LocalDateTime dataHoraAgendamento,
            Cliente cliente
    );

    List<Agendamento> findByDataHoraAgendamentoBetween(
            LocalDateTime dataHoraInicial,
            LocalDateTime dataHoraFinal
    );

    Agendamento findByDataHoraAgendamentoAndCliente(
            LocalDateTime dataHoraAgendamento,
            Cliente cliente
    );
}