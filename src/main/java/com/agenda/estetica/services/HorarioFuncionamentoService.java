package com.agenda.estetica.services;

import com.agenda.estetica.infrastructure.entity.Empresa;
import com.agenda.estetica.infrastructure.entity.HorarioFuncionamento;
import com.agenda.estetica.infrastructure.repository.HorarioFuncionamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioFuncionamentoService {

    private final HorarioFuncionamentoRepository horarioFuncionamentoRepository;

    public HorarioFuncionamento cadastrarHorario(HorarioFuncionamento horario) {

        if (horarioFuncionamentoRepository.existsByEmpresaAndDiaSemana(
                horario.getEmpresa(),
                horario.getDiaSemana())) {
            throw new RuntimeException("Já existe um horário cadastrado para esse dia!");
        }

        if (horario.getHoraInicio().isAfter(horario.getHoraFim())) {
            throw new RuntimeException("Hora de início não pode ser maior que hora de fim!");
        }

        return horarioFuncionamentoRepository.save(horario);
    }

    public HorarioFuncionamento buscarPorId(Long id) {
        return horarioFuncionamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horário não encontrado!"));
    }

    public HorarioFuncionamento buscarPorDia(Empresa empresa, DayOfWeek diaSemana) {
        return horarioFuncionamentoRepository.findByEmpresaAndDiaSemana(empresa, diaSemana)
                .orElseThrow(() -> new RuntimeException("Horário não encontrado para esse dia!"));
    }

    public List<HorarioFuncionamento> listarPorEmpresa(Empresa empresa) {
        return horarioFuncionamentoRepository.findByEmpresa(empresa);
    }

    public HorarioFuncionamento atualizarHorario(Long id, HorarioFuncionamento horarioAtualizado) {
        HorarioFuncionamento horario = buscarPorId(id);

        if (horarioAtualizado.getHoraInicio().isAfter(horarioAtualizado.getHoraFim())) {
            throw new RuntimeException("Hora de início não pode ser maior que hora de fim!");
        }

        horario.setHoraInicio(horarioAtualizado.getHoraInicio());
        horario.setHoraFim(horarioAtualizado.getHoraFim());

        return horarioFuncionamentoRepository.save(horario);
    }

    public void deletarHorario(Long id) {
        HorarioFuncionamento horario = buscarPorId(id);
        horarioFuncionamentoRepository.delete(horario);
    }
}