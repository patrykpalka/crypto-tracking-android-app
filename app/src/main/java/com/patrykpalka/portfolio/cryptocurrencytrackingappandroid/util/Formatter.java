package com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.util;

import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model.CoinMarketDataResponseDTO;
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model.CoinPriceResponseDTO;
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model.CoinsListDTO;
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model.CryptoPricesResponseDTO;

import java.util.List;

public class Formatter {
    public static String formatCryptoPrices(List<CryptoPricesResponseDTO> list) {
        StringBuilder builder = new StringBuilder();
        for (CryptoPricesResponseDTO dto : list) {
            builder.append(dto.symbol()).append(": ")
                    .append(dto.price()).append(" ")
                    .append(dto.currency()).append("\n");
        }
        return builder.toString();
    }

    public static String formatCoinsList(List<CoinsListDTO> list) {
        StringBuilder builder = new StringBuilder();
        for (CoinsListDTO dto : list) {
            builder.append(dto.symbol()).append(" - ")
                    .append(dto.name()).append("\n");
        }
        return builder.toString();
    }

    public static String formatHistory(List<CoinPriceResponseDTO> list) {
        StringBuilder builder = new StringBuilder();
        for (CoinPriceResponseDTO dto : list) {
            builder.append(dto.date()).append(": ")
                    .append(dto.price()).append(" ")
                    .append(dto.currency()).append("\n");
        }
        return builder.toString();
    }

    public static String formatMarketData(CoinMarketDataResponseDTO dto) {
        return "Symbol: " + dto.symbol() + "\n"
                + "Market Cap: " + dto.marketCap() + "\n"
                + "Volume 24h: " + dto.volume24h() + "\n"
                + "Circulating Supply: " + dto.circulatingSupply() + "\n"
                + "Waluta: " + dto.currency();
    }
}