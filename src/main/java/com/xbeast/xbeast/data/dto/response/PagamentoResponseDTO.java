package com.xbeast.xbeast.data.dto.response;

import com.xbeast.xbeast.data.entity.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoResponseDTO(
    Integer idPagamento,
    Integer idPedido,
    LocalDate dataPagamento,
    String tipoPagamento,
    BigDecimal valor
) {
    public PagamentoResponseDTO(Pagamento pagamento) {
        this(
            pagamento.getIdPagamento(),
            pagamento.getPedido() != null ? pagamento.getPedido().getIdPedido() : null,
            pagamento.getDataPagamento(),
            pagamento.getTipoPagamento(),
            pagamento.getValor()
        );
    }
}
