package com.xbeast.xbeast.repository;

import com.xbeast.xbeast.data.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByCategoriaIdCategoria(Integer idCategoria);
    List<Produto> findByEstoqueGreaterThan(int estoqueMinimo);
}
