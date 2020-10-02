package pl.olszak.currencies.domain.data

import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import pl.olszak.currencies.domain.data.model.CurrencyConverter
import pl.olszak.currencies.remote.CurrencyApi
import pl.olszak.currencies.remote.model.CurrenciesResponse

class CurrencyServiceFacadeTest {

    companion object {
        private val RESPONSE = CurrenciesResponse(
            baseCurrency = "EUR",
            rates = mapOf("USD" to "1")
        )
    }

    private val mockCurrencyApi: CurrencyApi = mock {
        on { getCurrencies(anyOrNull()) } doReturn Single.just(RESPONSE)
    }
    private val converterSpy: CurrencyConverter = spy(CurrencyConverter())

    private val service = CurrencyServiceFacade(
        currencyApi = mockCurrencyApi,
        converter = converterSpy
    )

    @Test
    fun `Delegate requests to api`() {
        service.getCurrencies().test()

        verify(mockCurrencyApi).getCurrencies(null)
    }

    @Test
    fun `Pass parameters for request`() {
        val code = "EUR"
        service.getCurrencies(code).test()

        verify(mockCurrencyApi).getCurrencies(code)
    }

    @Test
    fun `Convert response to domain representation`() {
        service.getCurrencies().test()

        verify(converterSpy).convert(RESPONSE)
    }
}
