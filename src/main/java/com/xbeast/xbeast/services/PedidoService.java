package com.xbeast.xbeast.services;

import com.xbeast.xbeast.data.dto.request.PedidoRequestDTO;
import com.xbeast.xbeast.data.dto.response.PedidoResponseDTO;
import com.xbeast.xbeast.data.dto.response.ClienteResponseDTO;
import com.xbeast.xbeast.data.dto.response.ItemPedidoResponseDTO;
import com.xbeast.xbeast.data.dto.response.PagamentoResponseDTO;
import com.xbeast.xbeast.data.entity.Cliente;
import com.xbeast.xbeast.data.entity.Pedido;
import com.xbeast.xbeast.repository.ClienteRepository;
import com.xbeast.xbeast.repository.PedidoRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ClienteRepository clienteRepository;
    
    @Transactional
    public List<PedidoResponseDTO> listarPorCliente(Integer idCliente) {
        List<Pedido> pedidos = pedidoRepository.findByClienteIdCliente(idCliente);
        return pedidos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public PedidoResponseDTO buscarPorId(Integer id) {
    Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    return toResponseDTO(pedido);
    }

    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(dto.dataPedido());
        pedido.setStatus(Pedido.Status.valueOf(dto.status()));
        pedido.setValorTotal(dto.valorTotal());

        Pedido salvo = pedidoRepository.save(pedido);
        return toResponseDTO(salvo);
    }

    public PedidoResponseDTO atualizar(Integer id, PedidoRequestDTO dto) {
        Pedido existente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        existente.setCliente(cliente);
        existente.setDataPedido(dto.dataPedido());
        existente.setStatus(Pedido.Status.valueOf(dto.status()));
        existente.setValorTotal(dto.valorTotal());

        Pedido atualizado = pedidoRepository.save(existente);
        return toResponseDTO(atualizado);
    }

    public void deletar(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedidoRepository.delete(pedido);
    }

    private PedidoResponseDTO toResponseDTO(Pedido pedido) {
    ClienteResponseDTO clienteDTO = new ClienteResponseDTO(pedido.getCliente());

    List<ItemPedidoResponseDTO> itens = pedido.getItens() != null
        ? pedido.getItens().stream()
            .map(ItemPedidoResponseDTO::new)
            .collect(Collectors.toList())
        : List.of();

    PagamentoResponseDTO pagamento = pedido.getPagamento() != null
        ? new PagamentoResponseDTO(pedido.getPagamento())
        : null;

    return new PedidoResponseDTO(
        pedido.getIdPedido(),
        pedido.getDataPedido(),
        pedido.getValorTotal(),
        pedido.getStatus().name(),
        clienteDTO,
        itens,
        pagamento
    );
}
 
}
