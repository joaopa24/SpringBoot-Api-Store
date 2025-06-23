package com.xbeast.xbeast.data.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoRequestDTO(
    Integer idPedido,
    String tipoPagamento,
    LocalDate dataPagamento,
    BigDecimal valor
) {}