package com.xbeast.xbeast.repository;

import com.xbeast.xbeast.data.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByClienteIdCliente(Integer idCliente);
    List<Pedido> findByStatus(String status);
}
