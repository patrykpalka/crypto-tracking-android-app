package com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model;

import java.math.BigDecimal;

public record CryptoPricesResponseDTO(
        String symbol,
        BigDecimal price,
        String currency
) {
}
