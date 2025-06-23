package com.xbeast.xbeast.data.dto.response;

import com.xbeast.xbeast.data.entity.Categoria;

public record CategoriaResponseDTO(
    Integer idCategoria,
    String nome,
    String descricao
) {
    public CategoriaResponseDTO(Categoria categoria) {
        this(
            categoria.getIdCategoria(),
            categoria.getNome(),
            categoria.getDescricao()
        );
    }
}
