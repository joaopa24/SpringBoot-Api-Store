package com.xbeast.xbeast.data.dto.response;

import com.xbeast.xbeast.data.entity.ItemPedido;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
    Integer idProduto,
    String nomeProduto,
    BigDecimal precoUnitario,
    Integer quantidade
) {
    public ItemPedidoResponseDTO(ItemPedido item) {
        this(
            item.getProduto().getIdProduto(),
            item.getProduto().getNome(),
            item.getPrecoUnitario(),
            item.getQuantidade()
        );
    }
}
