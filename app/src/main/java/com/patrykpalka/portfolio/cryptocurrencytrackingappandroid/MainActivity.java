package com.patrykpalka.portfolio.cryptocurrencytrackingappandroid;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView cryptoPricesTextView;
    private Button fetchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        cryptoPricesTextView = findViewById(R.id.cryptoPricesTextView);
        fetchButton = findViewById(R.id.fetchButton);

        fetchButton.setOnClickListener(v -> fetchCryptoPrices());
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
}