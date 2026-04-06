package com.agenda.estetica.controller;

import com.agenda.estetica.infrastructure.entity.Empresa;
import com.agenda.estetica.infrastructure.entity.Servico;
import com.agenda.estetica.services.EmpresaService;
import com.agenda.estetica.services.ServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService servicoService;
    private final EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<Servico> cadastrarServico(@RequestBody Servico servico) {
        return ResponseEntity.status(201).body(servicoService.cadastrarServico(servico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(servicoService.buscarPorId(id));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<Servico>> listarPorEmpresa(@PathVariable Long empresaId) {
        Empresa empresa = empresaService.buscarPorId(empresaId);
        return ResponseEntity.ok().body(servicoService.listarPorEmpresa(empresa));
    }

    @GetMapping("/empresa/{empresaId}/buscar")
    public ResponseEntity<List<Servico>> buscarPorNome(@PathVariable Long empresaId,
                                                       @RequestParam String nome) {
        Empresa empresa = empresaService.buscarPorId(empresaId);
        return ResponseEntity.ok().body(servicoService.buscarPorNome(empresa, nome));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long id,
                                                    @RequestBody Servico servico) {
        return ResponseEntity.ok().body(servicoService.atualizarServico(id, servico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        servicoService.deletarServico(id);
        return ResponseEntity.noContent().build();
    }
}