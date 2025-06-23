package com.xbeast.xbeast.services;

import com.xbeast.xbeast.data.dto.request.ItemPedidoRequestDTO;
import com.xbeast.xbeast.data.dto.response.ItemPedidoResponseDTO;
import com.xbeast.xbeast.data.entity.ItemPedido;
import com.xbeast.xbeast.data.entity.Pedido;
import com.xbeast.xbeast.data.entity.Produto;
import com.xbeast.xbeast.repository.ItemPedidoRepository;
import com.xbeast.xbeast.repository.PedidoRepository;
import com.xbeast.xbeast.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemPedidoService {

    @Autowired private ItemPedidoRepository itemPedidoRepository;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ProdutoRepository produtoRepository;

    public List<ItemPedidoResponseDTO> listarTodos() {
        return itemPedidoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ItemPedidoResponseDTO buscarPorId(Integer id) {
        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemPedido não encontrado com ID: " + id));
        return toResponseDTO(item);
    }

    public ItemPedidoResponseDTO criar(ItemPedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.idPedido())
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
        Produto produto = produtoRepository.findById(dto.idProduto())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(dto.quantidade());
        item.setPrecoUnitario(dto.precoUnitario());

        ItemPedido salvo = itemPedidoRepository.save(item);
        return toResponseDTO(salvo);
    }

    public ItemPedidoResponseDTO atualizar(Integer id, ItemPedidoRequestDTO dto) {
        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemPedido não encontrado com ID: " + id));

        Pedido pedido = pedidoRepository.findById(dto.idPedido())
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
        Produto produto = produtoRepository.findById(dto.idProduto())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(dto.quantidade());
        item.setPrecoUnitario(dto.precoUnitario());

        ItemPedido atualizado = itemPedidoRepository.save(item);
        return toResponseDTO(atualizado);
    }

    public void deletar(Integer id) {
        if (!itemPedidoRepository.existsById(id)) {
            throw new EntityNotFoundException("ItemPedido não encontrado para exclusão com ID: " + id);
        }
        itemPedidoRepository.deleteById(id);
    }

    private ItemPedidoResponseDTO toResponseDTO(ItemPedido item) {
        return new ItemPedidoResponseDTO(item);
    }
}
