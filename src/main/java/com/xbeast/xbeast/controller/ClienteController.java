package com.xbeast.xbeast.controller;

import java.util.List;

//import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.xbeast.xbeast.data.dto.request.ClienteRequestDTO;
import com.xbeast.xbeast.data.dto.response.ClienteResponseDTO;
import com.xbeast.xbeast.services.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Cliente", description = "Endpoints para gerenciar clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Endpoint para buscar uma pessoa por ID
    @Operation(
        summary = "Buscar pessoa por ID",
        description = "Recupera uma pessoa com base no seu ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
        }
    )
    @GetMapping("/{idPessoa}")
    public ResponseEntity<ClienteResponseDTO> encontrarPorId(@Parameter(description = "ID da pessoa") @PathVariable Long idPessoa) {
        ClienteResponseDTO pessoa = clienteService.encontrarPorId(idPessoa);
        return ResponseEntity.ok().body(pessoa);
    }

    // Endpoint para listar todas as pessoas
    @Operation(
        summary = "Listar todas as pessoas",
        description = "Recupera uma lista de todas as pessoas registradas",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de pessoas retornada")
        }
    )
    @GetMapping("/listar")
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        List<ClienteResponseDTO> pessoas = clienteService.listarTodos();
        return ResponseEntity.ok().body(pessoas);
    }

    // Endpoint para registrar uma nova pessoa
    @Operation(
        summary = "Registrar nova pessoa",
        description = "Registra uma nova pessoa no sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Pessoa registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
        }
    )
    @PostMapping("/registrar")
    public ResponseEntity<ClienteResponseDTO> registrar(@RequestBody @Parameter(description = "Dados da nova pessoa") ClienteRequestDTO pessoaRequestDTO) {
        if (pessoaRequestDTO == null || pessoaRequestDTO.nome() == null || pessoaRequestDTO.nome().isEmpty()) {
            //throw new BadRequestException("Dados inválidos para o registro da pessoa");
        }
        ClienteResponseDTO pessoa = clienteService.registrar(pessoaRequestDTO);
        return ResponseEntity.ok().body(pessoa);
    }

    // Endpoint para atualizar uma pessoa existente
    @Operation(
        summary = "Atualizar pessoa existente",
        description = "Atualiza os dados de uma pessoa registrada",
        responses = {
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
        }
    )
    @PutMapping("/atualizar/{idPessoa}")
    public ResponseEntity<ClienteResponseDTO> atualizar(
        @Parameter(description = "ID da pessoa") @PathVariable Long idPessoa,
        @RequestBody @Parameter(description = "Dados para atualização da pessoa") ClienteRequestDTO pessoaRequestDTO
    ) {
        ClienteResponseDTO pessoa = clienteService.atualizar(idPessoa, pessoaRequestDTO);
        if (pessoa == null) {
            //throw new BadRequestException("Pessoa não encontrada para atualização com ID: " + idPessoa);
        }
        return ResponseEntity.ok().body(pessoa);
    }

    // Endpoint para deletar uma pessoa
    @Operation(
        summary = "Deletar pessoa",
        description = "Deleta uma pessoa com base no seu ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Pessoa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
        }
    )
    @DeleteMapping("/deletar/{idPessoa}")
    public ResponseEntity<String> deletar(@Parameter(description = "ID da pessoa") @PathVariable Long idPessoa) {
        boolean deleted = clienteService.deletar(idPessoa);
        return ResponseEntity.ok().body("Pessoa deletada com sucesso");
    }
}