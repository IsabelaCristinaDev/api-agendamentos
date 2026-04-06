package com.agenda.estetica.infrastructure.repository;

import com.agenda.estetica.infrastructure.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Optional<Empresa> findByUsuarioEmail(String email);//Busca uma empresa pelo email do seu usuário

    boolean existsByCnpj(String cnpj);//Verifica se já existe uma empresa com aquele CNPJ antes de cadastrar

    boolean existsByUsuarioEmail(String email);//Verifica se já existe uma empresa com aquele email antes de cadastrar
    //Usado em conjunto com o UsuarioRepository para garantir que o email é único no sistema
}
