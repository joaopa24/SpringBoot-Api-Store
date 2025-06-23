package com.xbeast.xbeast.services;

import com.xbeast.xbeast.data.dto.request.PagamentoRequestDTO;
import com.xbeast.xbeast.data.dto.response.PagamentoResponseDTO;
import com.xbeast.xbeast.data.entity.Pagamento;
import com.xbeast.xbeast.data.entity.Pedido;
import com.xbeast.xbeast.repository.PagamentoRepository;
import com.xbeast.xbeast.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<PagamentoResponseDTO> listarTodos() {
        return pagamentoRepository.findAll()
                .stream()
                .map(PagamentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public PagamentoResponseDTO buscarPorId(Integer id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        return new PagamentoResponseDTO(pagamento);
    }

    public PagamentoResponseDTO criar(PagamentoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.idPedido())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + dto.idPedido()));

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setTipoPagamento(dto.tipoPagamento());
        pagamento.setDataPagamento(dto.dataPagamento());
        pagamento.setValor(dto.valor());

        pagamento = pagamentoRepository.save(pagamento);
        return new PagamentoResponseDTO(pagamento);
    }

    public PagamentoResponseDTO atualizar(Integer id, PagamentoRequestDTO dto) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        Pedido pedido = pedidoRepository.findById(dto.idPedido())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pagamento.setPedido(pedido);
        pagamento.setTipoPagamento(dto.tipoPagamento());
        pagamento.setDataPagamento(dto.dataPagamento());
        pagamento.setValor(dto.valor());

        pagamento = pagamentoRepository.save(pagamento);
        return new PagamentoResponseDTO(pagamento);
    }

    public void deletar(Integer id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        pagamentoRepository.delete(pagamento);
    }
}
