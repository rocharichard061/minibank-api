package com.minibank.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarContaRequest(

        @NotBlank(message = "O titular é obrigatório")
        @Size(max = 150, message = "O titular não pode ultrapassar 150 caracteres")
        String titular,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O formato do e-mail é inválido")
        @Size(max = 255, message = "O e-mail não pode ultrapassar 255 caracteres")
        String email

) {
}