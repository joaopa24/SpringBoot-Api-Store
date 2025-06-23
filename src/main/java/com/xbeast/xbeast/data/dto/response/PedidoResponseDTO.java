package com.xbeast.xbeast.data.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PedidoResponseDTO(
    Integer idPedido, // <== mudado
    LocalDate dataPedido,
    BigDecimal valorTotal,
    String status,
    ClienteResponseDTO cliente,
    List<ItemPedidoResponseDTO> itens,
    List<PagamentoResponseDTO> pagamentos
) {}