package com.agenda.estetica.controller;

import com.agenda.estetica.infrastructure.entity.Empresa;
import com.agenda.estetica.services.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<Empresa> cadastrarEmpresa(@RequestBody Empresa empresa) {
        return ResponseEntity.status(201).body(empresaService.cadastrarEmpresa(empresa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(empresaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id,
                                                    @RequestBody Empresa empresa) {
        return ResponseEntity.ok().body(empresaService.atualizarEmpresa(id, empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Long id) {
        empresaService.deletarEmpresa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> listarTodas() {
        return ResponseEntity.ok().body(empresaService.listarTodas());
    }
}