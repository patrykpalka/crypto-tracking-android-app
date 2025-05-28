package com.patrykpalka.portfolio.cryptocurrencytrackingappandroid;

import java.math.BigDecimal;

public record CryptoPricesResponseDTO(
        String symbol,
        BigDecimal price,
        String currency
) {
}
