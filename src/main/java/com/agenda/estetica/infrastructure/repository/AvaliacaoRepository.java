package com.agenda.estetica.infrastructure.repository;

import com.agenda.estetica.infrastructure.entity.Agendamento;
import com.agenda.estetica.infrastructure.entity.Avaliacao;

import com.agenda.estetica.infrastructure.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    Optional<Avaliacao> findByAgendamento(Agendamento agendamento);
    //Busca a avaliação de um agendamento específico

    boolean existsByAgendamento(Agendamento agendamento);
    //Verifica se aquele agendamento já foi avaliado antes de permitir uma nova avaliação.
    //Evita que o cliente avalie o mesmo atendimento duas vezes.

    List<Avaliacao> findByAgendamentoFuncionariaEmpresa(Empresa empresa);
    // Busca todas as avaliações de uma empresa navegando pelos relacionamentos automaticamente

    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.agendamento.funcionaria.empresa = :empresa")
        Double calcularMediaNotasByEmpresa(@Param("empresa") Empresa empresa);


    //é utilizada em frameworks ORM como o Spring Data JPA (Java) ou Jakarta Persistence
    // para calcular a média aritmética de uma coluna específica (nota) em uma entidade (Avaliacao),
    // retornando um valor numérico único

}
