package pl.olszak.currencies.domain.data.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import pl.olszak.currencies.remote.model.CurrenciesResponse
import java.math.BigDecimal

class CurrencyConverterTest {

    companion object {
        private const val USD = "USD"
        private const val PLN = "PLN"
        private const val BTC = "BTC"

        private val DEFAULT_RATE = BigDecimal.ONE
        private val ZERO = BigDecimal.ZERO
    }

    private val converter = CurrencyConverter()

    @Test
    fun `Convert empty response to empty list`() {
        val response = CurrenciesResponse()
        val result = converter.convert(response)

        assertThat(result).isEmpty()
    }

    @Test
    fun `Convert base currency to Currency with default rate`() {
        val response = CurrenciesResponse(baseCurrency = USD)
        val result = converter.convert(response)

        assertThat(result).isEqualTo(listOf(Currency(USD, DEFAULT_RATE)))
    }

    @Test
    fun `When base currency is empty list should be empty`() {
        val response = CurrenciesResponse(
            rates = mapOf(
                USD to "1",
                PLN to "1",
            )
        )

        val result = converter.convert(response)
        assertThat(result).isEmpty()
    }

    @Test
    fun `Convert response to proper currencies list`() {
        val response = CurrenciesResponse(
            baseCurrency = USD,
            rates = mapOf(
                PLN to "0",
                BTC to "0"
            )
        )

        val result = converter.convert(response)

        assertThat(result).hasSize(3)
        val expected = listOf(
            Currency(USD, DEFAULT_RATE),
            Currency(PLN, ZERO),
            Currency(BTC, ZERO)
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `Base currency should not appear twice on the list`() {
        val response = CurrenciesResponse(
            baseCurrency = USD,
            rates = mapOf(
                USD to "0",
                PLN to "0"
            )
        )

        val result = converter.convert(response)

        assertThat(result).hasSize(2)
        val expected = listOf(
            Currency(USD, DEFAULT_RATE),
            Currency(PLN, ZERO)
        )
        assertThat(result).isEqualTo(expected)
    }
}
