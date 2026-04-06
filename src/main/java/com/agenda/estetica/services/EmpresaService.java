package com.agenda.estetica.services;

import com.agenda.estetica.enums.Role;
import com.agenda.estetica.infrastructure.entity.Empresa;
import com.agenda.estetica.infrastructure.repository.EmpresaRepository;
import com.agenda.estetica.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;

    public Empresa cadastrarEmpresa(Empresa empresa) {

        if (usuarioRepository.existsByEmail(empresa.getUsuario().getEmail())) {
            throw new RuntimeException("Email já cadastrado!");
        }

        if (empresaRepository.existsByCnpj(empresa.getCnpj())) {
            throw new RuntimeException("CNPJ já cadastrado!");
        }

        empresa.getUsuario().setRole(Role.ROLE_EMPRESA);
        empresa.getUsuario().setAtivo(true);

        usuarioRepository.save(empresa.getUsuario());
        return empresaRepository.save(empresa);
    }

    public Empresa buscarPorId(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada!"));
    }

    public Empresa atualizarEmpresa(Long id, Empresa empresaAtualizada) {
        Empresa empresa = buscarPorId(id);

        empresa.setNome(empresaAtualizada.getNome());
        empresa.setTelefone(empresaAtualizada.getTelefone());
        empresa.setEndereco(empresaAtualizada.getEndereco());
        empresa.setFoto(empresaAtualizada.getFoto());
        empresa.setDescricao(empresaAtualizada.getDescricao());

        return empresaRepository.save(empresa);
    }

    public void deletarEmpresa(Long id) {
        Empresa empresa = buscarPorId(id);
        empresaRepository.delete(empresa);
        usuarioRepository.delete(empresa.getUsuario());
    }

    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }
}