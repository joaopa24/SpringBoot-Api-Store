package com.xbeast.xbeast.services;

import com.xbeast.xbeast.data.dto.request.ProdutoRequestDTO;
import com.xbeast.xbeast.data.dto.response.ProdutoResponseDTO;
import com.xbeast.xbeast.data.entity.Produto;
import com.xbeast.xbeast.repository.CategoriaRepository;
import com.xbeast.xbeast.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProdutoResponseDTO> listarTodosComEstoque() {
        return produtoRepository.findByEstoqueGreaterThan(0)
                .stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO buscarPorId(Integer idProduto) {
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + idProduto));
        return new ProdutoResponseDTO(produto);
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setMarca(dto.marca());
        produto.setEstoque(dto.estoque());
        produto.setCategoria(
            categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"))
        );
        return new ProdutoResponseDTO(produtoRepository.save(produto));
    }

    public ProdutoResponseDTO atualizar(Integer idProduto, ProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + idProduto));

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setMarca(dto.marca());
        produto.setEstoque(dto.estoque());
        produto.setCategoria(
            categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"))
        );

        return new ProdutoResponseDTO(produtoRepository.save(produto));
    }

    public void deletar(Integer idProduto) {
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + idProduto));
        produtoRepository.delete(produto);
    }
}
