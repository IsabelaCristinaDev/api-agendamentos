package com.agenda.estetica.infrastructure.repository;

import com.agenda.estetica.infrastructure.entity.Empresa;
import com.agenda.estetica.infrastructure.entity.HorarioFuncionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface HorarioFuncionamentoRepository extends JpaRepository<HorarioFuncionamento, Long> {

    List<HorarioFuncionamento> findByEmpresa(Empresa empresa);

    Optional<HorarioFuncionamento> findByEmpresaAndDiaSemana(Empresa empresa, DayOfWeek diaSemana);

    boolean existsByEmpresaAndDiaSemana(Empresa empresa, DayOfWeek diaSemana);
}