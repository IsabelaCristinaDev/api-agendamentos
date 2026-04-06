package com.agenda.estetica.services;

import com.agenda.estetica.enums.Role;
import com.agenda.estetica.infrastructure.entity.Empresa;
import com.agenda.estetica.infrastructure.entity.Funcionaria;
import com.agenda.estetica.infrastructure.repository.FuncionariaRepository;
import com.agenda.estetica.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionariaService {

    private final FuncionariaRepository funcionariaRepository;
    private final UsuarioRepository usuarioRepository;

    public Funcionaria cadastrarFuncionaria(Funcionaria funcionaria) {

        if (usuarioRepository.existsByEmail(funcionaria.getUsuario().getEmail())) {
            throw new RuntimeException("Email já cadastrado!");
        }

        if (funcionariaRepository.existsByNomeAndEmpresa(
                funcionaria.getNome(),
                funcionaria.getEmpresa())) {
            throw new RuntimeException("Funcionária já cadastrada nessa empresa!");
        }

        funcionaria.getUsuario().setRole(Role.ROLE_FUNCIONARIA);
        funcionaria.getUsuario().setAtivo(true);
        funcionaria.setAtivo(true);

        usuarioRepository.save(funcionaria.getUsuario());
        return funcionariaRepository.save(funcionaria);
    }

    public Funcionaria buscarPorEmail(String email) {
        return funcionariaRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new RuntimeException("Funcionária não encontrada!"));
    }

    public Funcionaria buscarPorId(Long id) {
        return funcionariaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionária não encontrada!"));
    }

    public List<Funcionaria> listarPorEmpresa(Empresa empresa) {
        return funcionariaRepository.findByEmpresa(empresa);
    }

    public List<Funcionaria> listarAtivasPorEmpresa(Empresa empresa) {
        return funcionariaRepository.findByEmpresaAndAtivo(empresa, true);
    }

    public Funcionaria atualizarFuncionaria(Long id, Funcionaria funcionariaAtualizada) {
        Funcionaria funcionaria = buscarPorId(id);

        funcionaria.setNome(funcionariaAtualizada.getNome());
        funcionaria.setFoto(funcionariaAtualizada.getFoto());
        funcionaria.setEspecialidades(funcionariaAtualizada.getEspecialidades());

        return funcionariaRepository.save(funcionaria);
    }

    public Funcionaria desativarFuncionaria(Long id) {
        Funcionaria funcionaria = buscarPorId(id);
        funcionaria.setAtivo(false);
        return funcionariaRepository.save(funcionaria);
    }

    public Funcionaria reativarFuncionaria(Long id) {
        Funcionaria funcionaria = buscarPorId(id);
        funcionaria.setAtivo(true);
        return funcionariaRepository.save(funcionaria);
    }

    public void deletarFuncionaria(Long id) {
        Funcionaria funcionaria = buscarPorId(id);
        funcionariaRepository.delete(funcionaria);
        usuarioRepository.delete(funcionaria.getUsuario());
    }
}