package com.xbeast.xbeast.data.dto.response;

import com.xbeast.xbeast.data.entity.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoResponseDTO(
    Integer idPagamento,
    LocalDate dataPagamento,
    String tipoPagamento,
    BigDecimal valor
) {
    public PagamentoResponseDTO(Pagamento pagamento) {
        this(
            pagamento.getIdPagamento(),
            pagamento.getDataPagamento(),
            pagamento.getTipoPagamento(),
            pagamento.getValor()
        );
    }
}
