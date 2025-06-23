package com.xbeast.xbeast.infra;

import com.xbeast.xbeast.data.entity.Cliente;
import com.xbeast.xbeast.repository.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner criarAdminInicial(ClienteRepository clienteRepository,
                                               PasswordEncoder passwordEncoder) {
        return args -> {
            String emailAdmin = "admin@email.com";

            if (clienteRepository.findByEmail(emailAdmin) == null) {
                Cliente admin = new Cliente();
                admin.setNome("Administrador");
                admin.setEmail(emailAdmin);
                admin.setCpf("00000000000");
                admin.setTelefone("000000000");
                admin.setEndereco("Endereço do Admin");
                admin.setSenha(passwordEncoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN"); // IMPORTANTE: já com ROLE_

                clienteRepository.save(admin);
                System.out.println("✅ Cliente ADMIN criado com sucesso.");
            } else {
                System.out.println("ℹ️ Cliente ADMIN já existe.");
            }
        };
    }
}
