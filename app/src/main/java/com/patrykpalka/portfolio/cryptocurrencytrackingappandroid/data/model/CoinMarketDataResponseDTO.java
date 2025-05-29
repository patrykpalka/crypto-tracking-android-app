package com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model;

import com.google.gson.annotations.SerializedName;

public record CoinMarketDataResponseDTO(
        String id,
        String symbol,
        @SerializedName("market_cap") long marketCap,
        @SerializedName("24h_volume") long volume24h,
        @SerializedName("circulating_supply") long circulatingSupply,
        String currency
) {
}