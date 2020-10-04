package pl.olszak.currencies.remote

interface CurrencyClient {

    fun createCurrencyApi(): CurrencyApi
}
