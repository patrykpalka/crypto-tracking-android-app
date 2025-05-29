# Cryptocurrency Tracking App Android

A demo Android application for tracking cryptocurrency prices, using a custom backend API.

## Table of Contents
- [Description](#description)
- [Tech Stack](#tech-stack)
- [Features](#features)
- [Screenshots](#screenshots)
- [API Endpoints](#api-endpoints)
- [Requirements and Running](#requirements-and-running)
- [Author](#author)

## Description
This project is an Android client for tracking cryptocurrency prices, historical data, supported coins, and market data. It communicates with a backend REST API (see [Cryptocurrency Tracking App Backend](https://github.com/patrykpalka/cryptocurrency-tracking-backend)).

## Tech Stack
- Java 17
- Android SDK 30+
- Retrofit 2 (networking)
- Gson (JSON parsing)
- Material Components
- Gradle (build tool)
- JUnit, Espresso (testing)

## Features
- Fetch current prices for selected cryptocurrencies.
- Display a list of supported coins.
- Fetch and display historical price data for a selected coin and date range.
- Fetch and display market data (market cap, volume, supply) for a selected coin.
- Simple, clear UI with Material Design components.
- Error handling for network/API issues.

## API Endpoints

- **GET /api/crypto/prices**  
  Returns current prices for selected cryptocurrencies.
- **GET /api/crypto/supported**  
  Returns a list of supported coins.
- **GET /api/crypto/history/{symbol}**  
  Returns historical prices for a selected cryptocurrency.
- **GET /api/crypto/market/{symbol}**  
  Returns market data for a selected cryptocurrency.

Example request:
```http
GET /api/crypto/prices?symbols=bitcoin,ethereum&currency=usd
```

## Requirements and Running

Requirements:
- Android Studio (Arctic Fox or newer)
- Android device or emulator (API 30+)
- Internet connection
- Running backend API (default: `http://10.0.2.2:8080/` for emulator)

To run the app locally:
1. Clone the repository.
2. Open in Android Studio.
3. Build and run on an emulator or device.

## Author

[Patryk Palka - GitHub](https://github.com/patrykpalka)
