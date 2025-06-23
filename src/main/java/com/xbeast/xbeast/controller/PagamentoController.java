package com.xbeast.xbeast.controller;

import com.xbeast.xbeast.data.dto.request.PagamentoRequestDTO;
import com.xbeast.xbeast.data.dto.response.PagamentoResponseDTO;
import com.xbeast.xbeast.services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pagamentoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pagamentoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> criar(@RequestBody PagamentoRequestDTO dto) {
        return ResponseEntity.ok(pagamentoService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> atualizar(@PathVariable Integer id, @RequestBody PagamentoRequestDTO dto) {
        return ResponseEntity.ok(pagamentoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pagamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
