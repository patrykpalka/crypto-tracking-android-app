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
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.api.RetrofitInstance;
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model.CoinMarketDataResponseDTO;
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model.CoinPriceResponseDTO;
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model.CoinsListDTO;
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.data.model.CryptoPricesResponseDTO;
import com.patrykpalka.portfolio.cryptocurrencytrackingappandroid.util.Formatter;

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

    private final ApiService apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        initViews();
        setListeners();
    }

    private void initViews() {
        cryptoPricesTextView = findViewById(R.id.cryptoPricesTextView);
        fetchButton = findViewById(R.id.fetchButton);
        fetchSupportedButton = findViewById(R.id.fetchSupportedButton);
        fetchHistoryButton = findViewById(R.id.fetchHistoryButton);
        fetchMarketDataButton = findViewById(R.id.fetchMarketDataButton);
        symbolEditText = findViewById(R.id.symbolEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        currencyEditText = findViewById(R.id.currencyEditText);
    }

    private void setListeners() {
        fetchButton.setOnClickListener(v -> fetchCryptoPrices());
        fetchSupportedButton.setOnClickListener(v -> fetchSupportedCoins());
        fetchHistoryButton.setOnClickListener(v -> fetchHistory());
        fetchMarketDataButton.setOnClickListener(v -> fetchMarketData());
    }

    private void fetchCryptoPrices() {
        apiService.getCryptoPrices().enqueue(new Callback<List<CryptoPricesResponseDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<CryptoPricesResponseDTO>> call, @NonNull Response<List<CryptoPricesResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cryptoPricesTextView.setText(Formatter.formatCryptoPrices(response.body()));
                } else {
                    setErrorText("Błąd pobierania danych.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CryptoPricesResponseDTO>> call, @NonNull Throwable t) {
                setErrorText("Błąd: " + t.getMessage());
                Log.e("API_ERROR", "Błąd pobierania danych", t);
            }
        });
    }

    private void fetchSupportedCoins() {
        apiService.getCoinsList().enqueue(new Callback<List<CoinsListDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<CoinsListDTO>> call, @NonNull Response<List<CoinsListDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cryptoPricesTextView.setText(Formatter.formatCoinsList(response.body()));
                } else {
                    setErrorText("Błąd pobierania listy.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CoinsListDTO>> call, @NonNull Throwable t) {
                setErrorText("Błąd: " + t.getMessage());
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
            setErrorText("Podaj symbol, datę początkową i końcową.");
            return;
        }
        if (currency.isEmpty()) {
            currency = "usd";
        }

        apiService.getHistoricalPriceData(symbol, start, end, currency).enqueue(new Callback<List<CoinPriceResponseDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<CoinPriceResponseDTO>> call, @NonNull Response<List<CoinPriceResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cryptoPricesTextView.setText(Formatter.formatHistory(response.body()));
                } else {
                    setErrorText("Błąd pobierania historii.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CoinPriceResponseDTO>> call, @NonNull Throwable t) {
                setErrorText("Błąd: " + t.getMessage());
                Log.e("API_ERROR", "Błąd pobierania historii", t);
            }
        });
    }

    private void fetchMarketData() {
        String symbol = symbolEditText.getText().toString().trim();
        String currency = currencyEditText.getText().toString().trim();
        if (symbol.isEmpty()) {
            setErrorText("Podaj symbol kryptowaluty.");
            return;
        }
        String currencyParam = currency.isEmpty() ? null : currency;

        apiService.getCryptocurrencyMarketData(symbol, currencyParam).enqueue(new Callback<CoinMarketDataResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<CoinMarketDataResponseDTO> call, @NonNull Response<CoinMarketDataResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cryptoPricesTextView.setText(Formatter.formatMarketData(response.body()));
                } else {
                    setErrorText("Błąd pobierania danych rynkowych.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CoinMarketDataResponseDTO> call, @NonNull Throwable t) {
                setErrorText("Błąd: " + t.getMessage());
                Log.e("API_ERROR", "Błąd pobierania danych rynkowych", t);
            }
        });
    }

    private void setErrorText(String message) {
        cryptoPricesTextView.setText(message);
    }
}
