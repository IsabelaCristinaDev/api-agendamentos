package com.agenda.estetica.services;

import com.agenda.estetica.infrastructure.entity.Empresa;
import com.agenda.estetica.infrastructure.entity.Servico;
import com.agenda.estetica.infrastructure.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public Servico cadastrarServico(Servico servico) {

        if (servicoRepository.existsByNomeAndEmpresa(
                servico.getNome(),
                servico.getEmpresa())) {
            throw new RuntimeException("Serviço já cadastrado nessa empresa!");
        }

        return servicoRepository.save(servico);
    }

    public Servico buscarPorId(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado!"));
    }

    public List<Servico> listarPorEmpresa(Empresa empresa) {
        return servicoRepository.findByEmpresa(empresa);
    }

    public List<Servico> buscarPorNome(Empresa empresa, String nome) {
        return servicoRepository.findByEmpresaAndNomeContainingIgnoreCase(empresa, nome);
    }

    public Servico atualizarServico(Long id, Servico servicoAtualizado) {
        Servico servico = buscarPorId(id);

        servico.setNome(servicoAtualizado.getNome());
        servico.setDuracaoEstimadaMinutos(servicoAtualizado.getDuracaoEstimadaMinutos());
        servico.setPreco(servicoAtualizado.getPreco());

        return servicoRepository.save(servico);
    }

    public void deletarServico(Long id) {
        Servico servico = buscarPorId(id);
        servicoRepository.delete(servico);
    }
}