package com.agenda.estetica.infrastructure.repository;

import com.agenda.estetica.infrastructure.entity.Empresa;
import com.agenda.estetica.infrastructure.entity.Funcionaria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FuncionariaRepository extends JpaRepository<Funcionaria, Long> {

    Optional<Funcionaria> findByUsuarioEmail(String email);

    List<Funcionaria> findByEmpresa(Empresa empresa);

    List<Funcionaria> findByEmpresaAndAtivo(Empresa empresa, Boolean ativo);

    boolean existsByNomeAndEmpresa(String nome, Empresa empresa);

    boolean existsByUsuarioEmail(String email);
}