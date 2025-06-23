package com.xbeast.xbeast.repository;

import com.xbeast.xbeast.data.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
    List<ItemPedido> findByPedidoIdPedido(Integer idPedido);
}
