package com.xbeast.xbeast.controller;

import com.xbeast.xbeast.data.dto.request.PedidoRequestDTO;
import com.xbeast.xbeast.data.dto.response.PedidoResponseDTO;
import com.xbeast.xbeast.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorCliente(@PathVariable Integer idCliente) {
        List<PedidoResponseDTO> pedidos = pedidoService.listarPorCliente(idCliente);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Integer id) {
        PedidoResponseDTO pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criar(@RequestBody PedidoRequestDTO pedidoDTO) {
        PedidoResponseDTO novoPedido = pedidoService.criar(pedidoDTO);
        return ResponseEntity.ok(novoPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizar(@PathVariable Integer id, @RequestBody PedidoRequestDTO pedidoDTO) {
        PedidoResponseDTO atualizado = pedidoService.atualizar(id, pedidoDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
