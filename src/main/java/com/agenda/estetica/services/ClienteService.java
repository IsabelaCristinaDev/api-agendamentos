package com.agenda.estetica.services;

import com.agenda.estetica.enums.Role;
import com.agenda.estetica.infrastructure.entity.Cliente;
import com.agenda.estetica.infrastructure.repository.ClienteRepository;
import com.agenda.estetica.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public Cliente cadastrarCliente(Cliente cliente) {

        if (usuarioRepository.existsByEmail(cliente.getUsuario().getEmail())) {
            throw new RuntimeException("Email já cadastrado!");
        }

        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new RuntimeException("CPF já cadastrado!");
        }

        cliente.getUsuario().setRole(Role.ROLE_CLIENTE);
        cliente.getUsuario().setAtivo(true);

        usuarioRepository.save(cliente.getUsuario());
        return clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
    }

    public Cliente atualizarCliente(Long id, Cliente clienteAtualizado) {
        Cliente cliente = buscarPorId(id);

        cliente.setNome(clienteAtualizado.getNome());
        cliente.setTelefone(clienteAtualizado.getTelefone());
        cliente.setFotoPerfil(clienteAtualizado.getFotoPerfil());

        return clienteRepository.save(cliente);
    }

    public void deletarCliente(Long id) {
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
        usuarioRepository.delete(cliente.getUsuario());
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
}