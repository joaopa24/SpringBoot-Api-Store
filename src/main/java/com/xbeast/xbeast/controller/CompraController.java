package com.xbeast.xbeast.controller;

import com.xbeast.xbeast.data.dto.request.CompraRequest;
import com.xbeast.xbeast.data.dto.request.PagamentoRequestDTO;
import com.xbeast.xbeast.data.dto.response.PagamentoResponseDTO;
import com.xbeast.xbeast.data.dto.response.PedidoResponseDTO;
import com.xbeast.xbeast.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> realizarCompra(@RequestBody CompraRequest request) {
        PedidoResponseDTO pedido = compraService.realizarCompra(request);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping("/finalizar-pagamento")
    public ResponseEntity<PagamentoResponseDTO> finalizarPagamento(@RequestBody PagamentoRequestDTO dto) {
    PagamentoResponseDTO pagamento = compraService.finalizarPagamento(dto);
    return ResponseEntity.ok(pagamento);
    }
}
