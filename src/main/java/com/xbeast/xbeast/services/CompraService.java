package com.xbeast.xbeast.services;

import com.xbeast.xbeast.data.dto.request.CompraRequest;
import com.xbeast.xbeast.data.dto.request.ItemPedidoRequestDTO;
import com.xbeast.xbeast.data.dto.request.PagamentoRequestDTO;
import com.xbeast.xbeast.data.dto.response.*;
import com.xbeast.xbeast.data.entity.*;
import com.xbeast.xbeast.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompraService {

    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ItemPedidoRepository itemPedidoRepository;
    @Autowired private PagamentoRepository pagamentoRepository;
    @Autowired private ClienteRepository clienteRepository;

    @Transactional
    public PedidoResponseDTO realizarCompra(CompraRequest request) {
        Cliente cliente = clienteRepository.findById(request.idCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDate.now());
        pedido.setStatus(Pedido.Status.PENDENTE);
        pedido.setValorTotal(BigDecimal.ZERO); // será calculado
        pedido = pedidoRepository.save(pedido);

        BigDecimal valorTotal = BigDecimal.ZERO;
        List<ItemPedidoResponseDTO> itensResponse = new ArrayList<>();

        for (ItemPedidoRequestDTO itemDTO : request.itens()) {
            Produto produto = produtoRepository.findById(itemDTO.idProduto())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDTO.idProduto()));

            if (itemDTO.quantidade() <= 0 || itemDTO.quantidade() > produto.getEstoque()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(itemDTO.quantidade()));
            valorTotal = valorTotal.add(subtotal);

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(produto.getPreco());
            itemPedidoRepository.save(item);

            produto.setEstoque(produto.getEstoque() - itemDTO.quantidade());
            produtoRepository.save(produto);

            itensResponse.add(new ItemPedidoResponseDTO(
            item.getProduto().getIdProduto(),
            item.getProduto().getNome(),
            item.getPrecoUnitario(),
            item.getQuantidade()
            ));
        }

        pedido.setValorTotal(valorTotal);
        pedidoRepository.save(pedido);

        PagamentoRequestDTO pagamentoDTO = request.pagamento();
        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setTipoPagamento(pagamentoDTO.tipoPagamento());
        pagamento.setDataPagamento(pagamentoDTO.dataPagamento() != null ? pagamentoDTO.dataPagamento() : LocalDate.now());
        pagamento.setValor(valorTotal);
        pagamentoRepository.save(pagamento);

        PagamentoResponseDTO pagamentoResponse = new PagamentoResponseDTO(pagamento);

        ClienteResponseDTO clienteResponse = new ClienteResponseDTO(cliente);

        return new PedidoResponseDTO(
            pedido.getIdPedido(),
            pedido.getDataPedido(),
            pedido.getValorTotal(),
            pedido.getStatus().name(),
            clienteResponse,
            itensResponse,
            pagamentoResponse
        );
    }
}
