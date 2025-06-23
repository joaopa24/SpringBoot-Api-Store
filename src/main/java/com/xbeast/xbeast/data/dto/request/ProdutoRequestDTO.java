package com.xbeast.xbeast.data.dto.request;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
    String nome,
    String descricao,
    BigDecimal preco,
    String marca,
    String urlImage,
    Integer estoque,
    Integer idCategoria
) {}
