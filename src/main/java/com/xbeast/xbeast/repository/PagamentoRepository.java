package com.xbeast.xbeast.repository;

import com.xbeast.xbeast.data.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    Pagamento findByPedidoIdPedido(Integer idPedido);
}
