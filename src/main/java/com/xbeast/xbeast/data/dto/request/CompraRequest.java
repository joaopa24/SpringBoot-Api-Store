package com.xbeast.xbeast.data.dto.request;

import java.util.List;

public record CompraRequest(
    Integer idCliente,
    List<ItemPedidoRequestDTO> itens,
    PagamentoRequestDTO pagamento
) {}
