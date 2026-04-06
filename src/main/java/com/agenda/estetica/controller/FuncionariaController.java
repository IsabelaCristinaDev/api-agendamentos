package com.agenda.estetica.controller;

import com.agenda.estetica.infrastructure.entity.Empresa;
import com.agenda.estetica.infrastructure.entity.Funcionaria;
import com.agenda.estetica.services.EmpresaService;
import com.agenda.estetica.services.FuncionariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarias")
@RequiredArgsConstructor
public class FuncionariaController {

    private final FuncionariaService funcionariaService;
    private final EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<Funcionaria> cadastrarFuncionaria(@RequestBody Funcionaria funcionaria) {
        return ResponseEntity.status(201).body(funcionariaService.cadastrarFuncionaria(funcionaria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionaria> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(funcionariaService.buscarPorId(id));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<Funcionaria>> listarPorEmpresa(@PathVariable Long empresaId) {
        Empresa empresa = empresaService.buscarPorId(empresaId);
        return ResponseEntity.ok().body(funcionariaService.listarPorEmpresa(empresa));
    }

    @GetMapping("/empresa/{empresaId}/ativas")
    public ResponseEntity<List<Funcionaria>> listarAtivasPorEmpresa(@PathVariable Long empresaId) {
        Empresa empresa = empresaService.buscarPorId(empresaId);
        return ResponseEntity.ok().body(funcionariaService.listarAtivasPorEmpresa(empresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionaria> atualizarFuncionaria(@PathVariable Long id,
                                                            @RequestBody Funcionaria funcionaria) {
        return ResponseEntity.ok().body(funcionariaService.atualizarFuncionaria(id, funcionaria));
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Funcionaria> desativarFuncionaria(@PathVariable Long id) {
        return ResponseEntity.ok().body(funcionariaService.desativarFuncionaria(id));
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<Funcionaria> reativarFuncionaria(@PathVariable Long id) {
        return ResponseEntity.ok().body(funcionariaService.reativarFuncionaria(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionaria(@PathVariable Long id) {
        funcionariaService.deletarFuncionaria(id);
        return ResponseEntity.noContent().build();
    }
}