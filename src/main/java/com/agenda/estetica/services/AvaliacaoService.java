package com.agenda.estetica.services;

import com.agenda.estetica.infrastructure.entity.Agendamento;
import com.agenda.estetica.infrastructure.entity.Avaliacao;
import com.agenda.estetica.infrastructure.entity.Empresa;
import com.agenda.estetica.infrastructure.repository.AvaliacaoRepository;
import com.agenda.estetica.infrastructure.repository.AgendamentoRepository;
import com.agenda.estetica.enums.StatusAgendamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AgendamentoRepository agendamentoRepository;

    public Avaliacao cadastrarAvaliacao(Avaliacao avaliacao) {

        Agendamento agendamento = agendamentoRepository.findById(
                        avaliacao.getAgendamento().getId())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado!"));

        if (!agendamento.getStatus().equals(StatusAgendamento.CONCLUIDO)) {
            throw new RuntimeException("Só é possível avaliar agendamentos concluídos!");
        }

        if (avaliacaoRepository.existsByAgendamento(agendamento)) {
            throw new RuntimeException("Esse agendamento já foi avaliado!");
        }

        if (avaliacao.getNota() < 1 || avaliacao.getNota() > 5) {
            throw new RuntimeException("A nota deve ser entre 1 e 5!");
        }

        return avaliacaoRepository.save(avaliacao);
    }

    public Avaliacao buscarPorAgendamento(Agendamento agendamento) {
        return avaliacaoRepository.findByAgendamento(agendamento)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada!"));
    }

    public List<Avaliacao> listarPorEmpresa(Empresa empresa) {
        return avaliacaoRepository.findByAgendamentoFuncionariaEmpresa(empresa);
    }

    public Double calcularMediaPorEmpresa(Empresa empresa) {
        Double media = avaliacaoRepository.calcularMediaNotasByEmpresa(empresa);
        return media != null ? media : 0.0;
    }
}