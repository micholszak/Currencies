package pl.olszak.currencies.remote.model

data class CurrenciesResponse(
    val baseCurrency: String = "",
    val rates: Map<String, String> = emptyMap()
)
