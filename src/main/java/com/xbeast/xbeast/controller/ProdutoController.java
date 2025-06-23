package com.xbeast.xbeast.controller;

import com.xbeast.xbeast.data.dto.request.ProdutoRequestDTO;
import com.xbeast.xbeast.data.dto.response.ProdutoResponseDTO;
import com.xbeast.xbeast.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping("/em-estoque")
    public ResponseEntity<List<ProdutoResponseDTO>> listarComEstoque() {
        return ResponseEntity.ok(produtoService.listarTodosComEstoque());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody ProdutoRequestDTO produtoRequest) {
        ProdutoResponseDTO criado = produtoService.criar(produtoRequest);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Integer id, @RequestBody ProdutoRequestDTO produtoRequest) {
        ProdutoResponseDTO atualizado = produtoService.atualizar(id, produtoRequest);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
