package com.xbeast.xbeast.data.dto.request;

import java.math.BigDecimal;

public record ItemPedidoRequestDTO(
    Integer idPedido,
    Integer idProduto,
    Integer quantidade,
    BigDecimal precoUnitario
) {}
