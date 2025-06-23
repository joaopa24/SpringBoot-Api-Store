package com.xbeast.xbeast.controller;

import com.xbeast.xbeast.data.dto.request.CategoriaRequestDTO;
import com.xbeast.xbeast.data.dto.response.CategoriaResponseDTO;
import com.xbeast.xbeast.data.entity.Categoria;
import com.xbeast.xbeast.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        List<Categoria> categorias = categoriaService.listarTodas();
        List<CategoriaResponseDTO> dtos = categorias.stream()
                .map(CategoriaResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Integer id) {
        Categoria categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(new CategoriaResponseDTO(categoria));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(@RequestBody CategoriaRequestDTO dto) {
        Categoria novaCategoria = new Categoria();
        novaCategoria.setNome(dto.nome());
        novaCategoria.setDescricao(dto.descricao());
        Categoria salva = categoriaService.criar(novaCategoria);
        return ResponseEntity.ok(new CategoriaResponseDTO(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Integer id, @RequestBody CategoriaRequestDTO dto) {
        Categoria atualizada = new Categoria();
        atualizada.setNome(dto.nome());
        atualizada.setDescricao(dto.descricao());
        Categoria categoriaAtualizada = categoriaService.atualizar(id, atualizada);
        return ResponseEntity.ok(new CategoriaResponseDTO(categoriaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
