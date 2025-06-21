package com.xbeast.xbeast.controller;

import com.xbeast.xbeast.infra.TokenService;
import com.xbeast.xbeast.data.dto.AuthenticationDTO;
import com.xbeast.xbeast.data.dto.response.LoginResponseDTO;
import com.xbeast.xbeast.data.dto.response.ClienteResponseDTO;
import com.xbeast.xbeast.data.entity.Cliente;
import com.xbeast.xbeast.repository.ClienteRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClienteRepository repository;

    @Operation(summary = "Login do cliente", description = "Autentica o cliente e retorna um token JWT para acesso.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados de login inválidos")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((Cliente) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception ex) {
            //throw new BadRequestException("Dados de login inválidos");
        }
        return null;
    }

    @Operation(summary = "Registro de novo cliente", description = "Cria um novo cliente no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Cliente já existe")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid Cliente data) {
        if (repository.findByEmail(data.getEmail()) != null) {
            //throw new BadRequestException("Cliente já existe");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getSenha());
        Cliente novoCliente = new Cliente();
        novoCliente.setEmail(data.getEmail());
        novoCliente.setNome(data.getNome());
        novoCliente.setTelefone(data.getTelefone());
        novoCliente.setEndereco(data.getEndereco());
        novoCliente.setCpf(data.getCpf());
        novoCliente.setSenha(encryptedPassword);
        novoCliente.setRole("ROLE_CLIENTE");

        repository.save(novoCliente);

        return ResponseEntity.ok("Cliente registrado com sucesso!");
    }

    @Operation(summary = "Verificar cliente autenticado", description = "Retorna os dados do cliente atualmente autenticado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente autenticado retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Cliente não autenticado")
    })
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            //throw new BadRequestException("Cliente não autenticado");
        }

        Cliente cliente = (Cliente) authentication.getPrincipal();
        ClienteResponseDTO responseDTO = new ClienteResponseDTO(cliente);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
