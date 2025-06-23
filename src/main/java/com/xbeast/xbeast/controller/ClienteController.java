package com.xbeast.xbeast.controller;

import com.xbeast.xbeast.data.dto.request.ClienteRequestDTO;
import com.xbeast.xbeast.data.dto.response.ClienteResponseDTO;
import com.xbeast.xbeast.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Cliente", description = "Endpoints para gerenciar clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Buscar cliente por ID", responses = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{idPessoa}")
    public ResponseEntity<ClienteResponseDTO> encontrarPorId(
        @Parameter(description = "ID do cliente") @PathVariable Integer idPessoa
    ) {
        ClienteResponseDTO cliente = clienteService.encontrarPorId(idPessoa);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Listar todos os clientes", responses = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes retornada")
    })
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @Operation(summary = "Registrar novo cliente", responses = {
        @ApiResponse(responseCode = "200", description = "Cliente registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registrar(
        @RequestBody @Parameter(description = "Dados do novo cliente") ClienteRequestDTO dto
    ) {
        ClienteResponseDTO cliente = clienteService.registrar(dto);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Atualizar cliente existente", responses = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping("/{idPessoa}")
    public ResponseEntity<ClienteResponseDTO> atualizar(
        @PathVariable Integer idPessoa,
        @RequestBody @Parameter(description = "Dados atualizados do cliente") ClienteRequestDTO dto
    ) {
        ClienteResponseDTO atualizado = clienteService.atualizar(idPessoa, dto);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Deletar cliente", responses = {
        @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @DeleteMapping("/{idPessoa}")
    public ResponseEntity<String> deletar(@PathVariable Integer idPessoa) {
        clienteService.deletar(idPessoa);
        return ResponseEntity.ok("Cliente deletado com sucesso");
    }
}
