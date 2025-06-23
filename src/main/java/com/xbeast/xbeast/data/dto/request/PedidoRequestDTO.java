package com.xbeast.xbeast.data.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PedidoRequestDTO(
    Integer idCliente,
    LocalDate dataPedido,
    String status,
    BigDecimal valorTotal,
    List<ItemPedidoRequestDTO> itens,
    PagamentoRequestDTO pagamento
) {}
