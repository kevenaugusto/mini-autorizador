package br.com.kevenaugusto.miniauthorizer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record InputCardDto(
        @NotBlank(message = "O cartão informado é inválido.") @Size(min = 16, max = 16, message = "O cartão deve ser composto por 16 números.")
        @Pattern(regexp = "^\\d+$", message = "Somente números são permitidos no cartão.") String numeroCartao,
        @NotBlank(message = "A senha informada é inválida.") String senha) {
}
