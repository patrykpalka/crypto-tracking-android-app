package com.patrykpalka.portfolio.cryptocurrencytrackingappandroid;

public record CoinPriceResponseDTO(
        String date,
        float price,
        String currency
) {
}
