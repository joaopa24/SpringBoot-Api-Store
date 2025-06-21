package com.xbeast.xbeast.data.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public record ClienteRequestDTO(

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 80, message = "O nome não pode ter mais de 80 caracteres")
    String nome,

    @NotBlank(message = "O endereço é obrigatório")
    String endereco,

    @NotBlank(message = "O telefone é obrigatório")
    @Size(min = 8, max = 20, message = "O telefone deve ter entre 8 e 20 caracteres")
    String telefone,

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 14, message = "O CPF deve ter entre 11 e 14 caracteres")
    String cpf,

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    @Size(max = 80, message = "O email não pode ter mais de 80 caracteres")
    String email,

    @Size(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres")
    String senha,

    @Size(min = 4, max = 20, message = "O papel deve ser algo como ROLE_USER ou ROLE_ADMIN")
    String role

) {
    public ClienteRequestDTO {
        if (senha != null && !senha.isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            senha = encoder.encode(senha);
        }
    }
}
