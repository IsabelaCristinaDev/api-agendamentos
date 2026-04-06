package com.agenda.estetica.infrastructure.repository;

import com.agenda.estetica.infrastructure.entity.Empresa;
import com.agenda.estetica.infrastructure.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> findByEmpresa(Empresa empresa);//Busca todos os serviços de uma empresa específica
    //Usado quando o cliente acessa o perfil do estúdio e quer ver todos os serviços disponíveis,
    // ou quando a empresa quer gerenciar seus serviços.

    List<Servico> findByEmpresaAndNomeContainingIgnoreCase(Empresa empresa, String nome);
    //Busca serviços de uma empresa filtrando pelo nome, ignorando maiúsculas e minúsculas.

    boolean existsByNomeAndEmpresa(String nome, Empresa empresa);
    //Verifica se já existe um serviço com aquele nome naquela empresa antes de cadastrar
}