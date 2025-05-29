package com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model;

public record CoinPriceResponseDTO(
        String date,
        float price,
        String currency
) {
}
