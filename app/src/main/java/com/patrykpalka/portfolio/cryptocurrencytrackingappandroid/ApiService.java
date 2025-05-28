package com.patrykpalka.portfolio.cryptocurrencytrackingappandroid;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/crypto/prices")
    Call<List<CryptoPricesResponseDTO>> getCryptoPrices();

    @GET("/api/crypto/supported")
    Call<List<CoinsListDTO>> getCoinsList();

    @GET("/api/crypto/history/{symbol}")
    Call<List<CoinPriceResponseDTO>> getHistoricalPriceData(
            @Path("symbol") String symbol,
            @Query("start") String start,
            @Query("end") String end,
            @Query("currency") String currency
    );

    @GET("/api/crypto/market/{symbol}")
    Call<CoinMarketDataResponseDTO> getCryptocurrencyMarketData(
            @Path("symbol") String symbol,
            @Query("currency") String currency
    );
}
