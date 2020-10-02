package pl.olszak.currencies.domain.data.model

import pl.olszak.currencies.core.toBigDecimal
import pl.olszak.currencies.remote.model.CurrenciesResponse
import java.math.BigDecimal
import javax.inject.Inject

class CurrencyConverter @Inject constructor() {

    companion object {
        private val DEFAULT_RATE = BigDecimal.ONE
    }

    fun convert(response: CurrenciesResponse): List<Currency> {
        if (response.baseCurrency.isEmpty()) return emptyList()

        val currencies = response.rates.filterKeys { key ->
            key != response.baseCurrency
        }.map { (code, rate) ->
            Currency(
                code = code,
                rate = rate.toBigDecimal(default = DEFAULT_RATE)
            )
        }

        return listOf(Currency(response.baseCurrency, DEFAULT_RATE))
            .plus(currencies)
    }
}
