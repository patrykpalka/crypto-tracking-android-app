package com.patrykpalka.portfolio.cryptocurrencytrackingappandroid;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/api/crypto/prices")
    Call<List<CryptoPricesResponseDTO>> getCryptoPrices();

    @GET("/api/crypto/supported")
    Call<List<CoinsListDTO>> getCoinsList();
}
