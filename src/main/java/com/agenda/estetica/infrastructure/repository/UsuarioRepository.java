package com.agenda.estetica.infrastructure.repository;

import com.agenda.estetica.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);//Busca um usuário pelo email.

    boolean existsByEmail(String email);//Verifica se já existe um usuário com aquele email antes de cadastrar.


}
