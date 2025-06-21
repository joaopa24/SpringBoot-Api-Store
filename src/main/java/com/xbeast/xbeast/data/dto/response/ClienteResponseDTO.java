package com.xbeast.xbeast.data.dto.response;

import com.xbeast.xbeast.data.entity.Cliente;

public record ClienteResponseDTO(
    Integer idCliente,
    String nome,
    String email,
    String telefone,
    String endereco,
    String cpf
) {
    public ClienteResponseDTO(Cliente cliente) {
        this(
            cliente.getIdCliente(),
            cliente.getNome(),
            cliente.getEmail(),
            cliente.getTelefone(),
            cliente.getEndereco(),
            cliente.getCpf()
        );
    }
}
