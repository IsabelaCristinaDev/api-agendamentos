package com.agenda.estetica.infrastructure.repository;

import com.agenda.estetica.infrastructure.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    //Herda todos os métodos prontos do Spring Data JPA, nao precisa escrever na mao
    //save() → salvar ou atualizar; findById() → buscar por id; findAll() → listar todos;
    //deleteById() → deletar por id.

    Optional<Cliente>  findByUsuarioEmail(String email); // Busca um cliente pelo email do seu usuário.

    boolean existsByCpf(String cpf);//Verifica se já existe um cliente com aquele CPF antes de cadastrar.

    boolean existsByUsuarioEmail(String email);// verifica se já existe um cliente com aquele email antes de cadastrar.

}
