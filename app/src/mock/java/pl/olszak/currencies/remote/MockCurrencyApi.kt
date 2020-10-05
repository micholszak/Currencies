package pl.olszak.currencies.remote

import io.reactivex.rxjava3.core.Single
import pl.olszak.currencies.remote.model.CurrenciesResponse
import java.net.UnknownHostException

class MockCurrencyApi() : CurrencyApi {

    companion object {
        var simulateErrorResponse = false

        private val currencies: List<String> = listOf(
            "EUR",
            "PLN",
            "USD",
            "AUD",
            "BRL",
            "CAD"
        )

        private const val RATE = "1"
    }

    override fun getCurrencies(currency: String?): Single<CurrenciesResponse> =
        Single.create { emitter ->
            if (simulateErrorResponse) {
                emitter.onError(UnknownHostException())
            }

            val enteredCurrency: String = currency.orEmpty()
            val baseCurrency: String = if (currencies.contains(enteredCurrency)) {
                enteredCurrency
            } else {
                "EUR"
            }

            val rates =
                currencies.filterNot { value ->
                    value == baseCurrency
                }.sorted()
                    .associateWith { RATE }
            val response = CurrenciesResponse(
                baseCurrency = baseCurrency,
                rates = rates
            )
            emitter.onSuccess(response)
        }
}
