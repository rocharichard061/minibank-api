package com.minibank.api.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ContaResponse(
        UUID id,
        String titular,
        String email,
        BigDecimal saldo,
        Instant criadoEm
) {
}
