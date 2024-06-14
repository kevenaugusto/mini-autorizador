package br.com.kevenaugusto.miniauthorizer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record TransactionDto(
        @NotBlank(message = "O cartão informado é inválido.") @Size(min = 16, max = 16, message = "O cartão deve ser composto por 16 números.")
        @Pattern(regexp = "^\\d+$", message = "Somente números são permitidos no cartão.") String numeroCartao,
        @NotBlank(message = "A senha informada é inválida.") String senhaCartao,
        @NotNull(message = "O valor informado é inválido.") BigDecimal valor) {
}
