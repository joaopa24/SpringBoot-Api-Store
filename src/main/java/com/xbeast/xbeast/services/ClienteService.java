package com.xbeast.xbeast.services;

import com.xbeast.xbeast.data.dto.request.ClienteRequestDTO;
import com.xbeast.xbeast.data.dto.response.ClienteResponseDTO;
import com.xbeast.xbeast.data.entity.Cliente;
import com.xbeast.xbeast.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteResponseDTO encontrarPorId(Long idCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (cliente.isPresent()) {
            return new ClienteResponseDTO(cliente.get());
        } else {
            throw new RuntimeException("Cliente não encontrado para o ID: " + idCliente);
        }
    }

    public List<ClienteResponseDTO> listarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(ClienteResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO registrar(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteRequestDTO.nome());
        cliente.setEndereco(clienteRequestDTO.endereco());
        cliente.setTelefone(clienteRequestDTO.telefone());
        cliente.setEmail(clienteRequestDTO.email());
        cliente.setCpf(clienteRequestDTO.cpf());

        cliente.setRole("ROLE_CLIENTE");
        cliente.setSenha(clienteRequestDTO.senha());

        Cliente clienteSalvo = clienteRepository.save(cliente);
        return new ClienteResponseDTO(clienteSalvo);
    }

    public ClienteResponseDTO atualizar(Long idCliente, ClienteRequestDTO clienteRequestDTO) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(idCliente);
        if (clienteExistente.isPresent()) {
            Cliente cliente = clienteExistente.get();
            cliente.setNome(clienteRequestDTO.nome());
            cliente.setEndereco(clienteRequestDTO.endereco());
            cliente.setTelefone(clienteRequestDTO.telefone());
            cliente.setEmail(clienteRequestDTO.email());
            cliente.setCpf(clienteRequestDTO.cpf());

            if (clienteRequestDTO.senha() != null && !clienteRequestDTO.senha().isEmpty()) {
                cliente.setSenha(clienteRequestDTO.senha());
            }

            Cliente clienteAtualizado = clienteRepository.save(cliente);
            return new ClienteResponseDTO(clienteAtualizado);
        } else {
            throw new RuntimeException("Cliente não encontrado para o ID: " + idCliente);
        }
    }

    public boolean deletar(Long idCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (cliente.isPresent()) {
            clienteRepository.deleteById(idCliente);
            return true;
        } else {
            throw new RuntimeException("Cliente não encontrado para o ID: " + idCliente);
        }
    }
}
