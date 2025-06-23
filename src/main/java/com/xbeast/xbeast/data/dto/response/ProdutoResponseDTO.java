package com.xbeast.xbeast.data.dto.response;

import com.xbeast.xbeast.data.entity.Produto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
    Integer idProduto,
    String nome,
    String descricao,
    BigDecimal preco,
    String marca,
    String urlImage,
    Integer estoque,
    CategoriaResponseDTO categoria
) {
    public ProdutoResponseDTO(Produto produto) {
        this(
            produto.getIdProduto(),
            produto.getNome(),
            produto.getDescricao(),
            produto.getPreco(),
            produto.getMarca(),
            produto.getUrl_image(),
            produto.getEstoque(),
            new CategoriaResponseDTO(produto.getCategoria())
        );
    }
}
