package com.xbeast.xbeast.data.dto.request;

public record ClienteRequestDTO(
    String nome,
    String email,
    String telefone,
    String endereco,
    String cpf,
    String senha
) {}
