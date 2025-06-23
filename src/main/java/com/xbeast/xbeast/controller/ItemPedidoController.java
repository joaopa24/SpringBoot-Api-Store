package com.xbeast.xbeast.controller;

import com.xbeast.xbeast.data.dto.request.ItemPedidoRequestDTO;
import com.xbeast.xbeast.data.dto.response.ItemPedidoResponseDTO;
import com.xbeast.xbeast.services.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-pedido")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService itemPedidoService;

    @GetMapping
    public ResponseEntity<List<ItemPedidoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(itemPedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(itemPedidoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ItemPedidoResponseDTO> criar(@RequestBody ItemPedidoRequestDTO dto) {
        return ResponseEntity.ok(itemPedidoService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoResponseDTO> atualizar(@PathVariable Integer id, @RequestBody ItemPedidoRequestDTO dto) {
        return ResponseEntity.ok(itemPedidoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        itemPedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
