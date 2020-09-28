package pl.olszak.currencies.remote.model

import androidx.annotation.Keep

@Keep
data class CurrenciesResponse(
    val baseCurrency: String = "",
    val rates: Map<String, String> = emptyMap()
)
