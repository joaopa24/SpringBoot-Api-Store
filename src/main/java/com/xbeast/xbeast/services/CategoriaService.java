package com.xbeast.xbeast.services;

import com.xbeast.xbeast.data.entity.Categoria;
import com.xbeast.xbeast.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com ID: " + id));
    }

    public Categoria criar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Integer id, Categoria categoriaAtualizada) {
        Categoria existente = buscarPorId(id);
        existente.setNome(categoriaAtualizada.getNome());
        existente.setDescricao(categoriaAtualizada.getDescricao());
        return categoriaRepository.save(existente);
    }

    public void deletar(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoria não encontrada para exclusão com ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
