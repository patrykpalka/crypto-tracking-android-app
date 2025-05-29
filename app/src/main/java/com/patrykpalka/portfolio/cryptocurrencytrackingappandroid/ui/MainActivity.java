package com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.R;
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.api.ApiService;
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model.CoinMarketDataResponseDTO;
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model.CoinPriceResponseDTO;
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model.CoinsListDTO;
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model.CryptoPricesResponseDTO;
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.api.RetrofitInstance;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView cryptoPricesTextView;
    private Button fetchButton;
    private Button fetchSupportedButton;
    private Button fetchHistoryButton;
    private Button fetchMarketDataButton;
    private EditText symbolEditText;
    private EditText startDateEditText;
    private EditText endDateEditText;
    private EditText currencyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        cryptoPricesTextView = findViewById(R.id.cryptoPricesTextView);
        fetchButton = findViewById(R.id.fetchButton);
        fetchSupportedButton = findViewById(R.id.fetchSupportedButton);
        fetchHistoryButton = findViewById(R.id.fetchHistoryButton);
        fetchMarketDataButton = findViewById(R.id.fetchMarketDataButton);
        symbolEditText = findViewById(R.id.symbolEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        currencyEditText = findViewById(R.id.currencyEditText);

        fetchButton.setOnClickListener(v -> fetchCryptoPrices());
        fetchSupportedButton.setOnClickListener(v -> fetchSupportedCoins());
        fetchHistoryButton.setOnClickListener(v -> fetchHistory());
        fetchMarketDataButton.setOnClickListener(v -> fetchMarketData());
    }

    private void fetchCryptoPrices() {
        ApiService apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
        Call<List<CryptoPricesResponseDTO>> call = apiService.getCryptoPrices();
        call.enqueue(new Callback<List<CryptoPricesResponseDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<CryptoPricesResponseDTO>> call, @NonNull Response<List<CryptoPricesResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CryptoPricesResponseDTO> cryptoPrices = response.body();
                    StringBuilder builder = new StringBuilder();
                    for (CryptoPricesResponseDTO dto : cryptoPrices) {
                        builder.append(dto.symbol()).append(": ")
                                .append(dto.price()).append(" ")
                                .append(dto.currency()).append("\n");
                    }
                    cryptoPricesTextView.setText(builder.toString());
                } else {
                    cryptoPricesTextView.setText("Błąd pobierania danych.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CryptoPricesResponseDTO>> call, @NonNull Throwable t) {
                cryptoPricesTextView.setText("Błąd: " + t.getMessage());
                Log.e("API_ERROR", "Błąd pobierania danych", t);
            }
        });
    }

    private void fetchSupportedCoins() {
        ApiService apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
        Call<List<CoinsListDTO>> call = apiService.getCoinsList();
        call.enqueue(new Callback<List<CoinsListDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<CoinsListDTO>> call, @NonNull Response<List<CoinsListDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CoinsListDTO> coins = response.body();
                    StringBuilder builder = new StringBuilder();
                    for (CoinsListDTO dto : coins) {
                        builder.append(dto.symbol()).append(" - ").append(dto.name()).append("\n");
                    }
                    cryptoPricesTextView.setText(builder.toString());
                } else {
                    cryptoPricesTextView.setText("Błąd pobierania listy.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CoinsListDTO>> call, @NonNull Throwable t) {
                cryptoPricesTextView.setText("Błąd: " + t.getMessage());
                Log.e("API_ERROR", "Błąd pobierania listy", t);
            }
        });
    }

    private void fetchHistory() {
        String symbol = symbolEditText.getText().toString().trim();
        String start = startDateEditText.getText().toString().trim();
        String end = endDateEditText.getText().toString().trim();
        String currency = currencyEditText.getText().toString().trim();

        if (symbol.isEmpty() || start.isEmpty() || end.isEmpty()) {
            cryptoPricesTextView.setText("Podaj symbol, datę początkową i końcową.");
            return;
        }
        if (currency.isEmpty()) {
            currency = "usd";
        }

        ApiService apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
        Call<List<CoinPriceResponseDTO>> call = apiService.getHistoricalPriceData(symbol, start, end, currency);
        call.enqueue(new Callback<List<CoinPriceResponseDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<CoinPriceResponseDTO>> call, @NonNull Response<List<CoinPriceResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CoinPriceResponseDTO> history = response.body();
                    StringBuilder builder = new StringBuilder();
                    for (CoinPriceResponseDTO dto : history) {
                        builder.append(dto.date()).append(": ")
                                .append(dto.price()).append(" ")
                                .append(dto.currency()).append("\n");
                    }
                    cryptoPricesTextView.setText(builder.toString());
                } else {
                    cryptoPricesTextView.setText("Błąd pobierania historii.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CoinPriceResponseDTO>> call, @NonNull Throwable t) {
                cryptoPricesTextView.setText("Błąd: " + t.getMessage());
                Log.e("API_ERROR", "Błąd pobierania historii", t);
            }
        });
    }

    private void fetchMarketData() {
        String symbol = symbolEditText.getText().toString().trim();
        String currency = currencyEditText.getText().toString().trim();
        if (symbol.isEmpty()) {
            cryptoPricesTextView.setText("Podaj symbol kryptowaluty.");
            return;
        }
        String currencyParam = currency.isEmpty() ? null : currency;

        ApiService apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
        Call<CoinMarketDataResponseDTO> call = apiService.getCryptocurrencyMarketData(symbol, currencyParam);
        call.enqueue(new Callback<CoinMarketDataResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<CoinMarketDataResponseDTO> call, @NonNull Response<CoinMarketDataResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CoinMarketDataResponseDTO dto = response.body();
                    String result = "Symbol: " + dto.symbol() + "\n"
                            + "Market Cap: " + dto.marketCap() + "\n"
                            + "Volume 24h: " + dto.volume24h() + "\n"
                            + "Circulating Supply: " + dto.circulatingSupply() + "\n"
                            + "Waluta: " + dto.currency();
                    cryptoPricesTextView.setText(result);
                } else {
                    cryptoPricesTextView.setText("Błąd pobierania danych rynkowych.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CoinMarketDataResponseDTO> call, @NonNull Throwable t) {
                cryptoPricesTextView.setText("Błąd: " + t.getMessage());
                Log.e("API_ERROR", "Błąd pobierania danych rynkowych", t);
            }
        });
    }
}