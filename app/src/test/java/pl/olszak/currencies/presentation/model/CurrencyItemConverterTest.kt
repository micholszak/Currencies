package pl.olszak.currencies.presentation.model

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import pl.olszak.currencies.domain.data.model.Currency
import pl.olszak.currencies.presentation.model.provider.CurrencyItemConverter
import pl.olszak.currencies.presentation.model.provider.CurrencyNameProvider
import pl.olszak.currencies.presentation.model.provider.FlagProvider
import pl.olszak.currencies.view.adapter.model.Flag
import java.math.BigDecimal

class CurrencyItemConverterTest {

    companion object {
        private val CODES = listOf(
            "PLN", "USD", "YEN", "EUR", "AUD", "BGN"
        )

        private fun randomCode(): String = CODES.random()
    }

    private val mockFlagProvider: FlagProvider = mock {
        on { forCurrency(any()) } doReturn Flag()
    }
    private val mockNameProvider: CurrencyNameProvider = mock {
        on { forCode(any()) } doReturn ""
    }
    private val converter = CurrencyItemConverter(
        flagProvider = mockFlagProvider,
        nameProvider = mockNameProvider
    )

    @Test
    fun `Empty currency list converts to empty list`() {
        val result = converter.convertFor("10", emptyList())

        assertThat(result).isEmpty()
    }

    @Test
    fun `Current value is copied to first currency from list`() {
        val currentValue = "5.15"
        val currencies = createCurrencies(size = 1, BigDecimal.ONE)

        val result = converter.convertFor(currentValue, currencies)

        assertThat(result).hasSize(1)
        val first = result.first()
        assertThat(first.amount).isEqualTo(currentValue)
    }

    @Test
    fun `For a value that cannot be converted to BigDecimal fall back to rate 0`() {
        val invalidCurrentValue = "aabads"
        val currencies = createCurrencies(size = 2, value = BigDecimal.ONE)

        val result = converter.convertFor(invalidCurrentValue, currencies)

        val last = result.last()
        assertThat(last.amount).isEqualTo("0.00")
    }

    @Test
    fun `Properly convert values to adapter representation`() {
        val currentValue = "5.13"
        val currencies = createCurrencies(5, BigDecimal.ONE)

        val result = converter.convertFor(currentValue, currencies)

        result.forEach { item ->
            assertThat(item.amount).isEqualTo(currentValue)
        }
    }

    @Test
    fun `All amounts should be formatted to 2 decimal places`() {
        val currentValue = "5.1623"
        val currencies = createCurrencies(10, BigDecimal.TEN)

        val result = converter.convertFor(currentValue, currencies)
        val resultTrimmed = result.subList(1, currencies.lastIndex)

        resultTrimmed.forEach { item ->
            assertThat(item.amount).isEqualTo("51.62")
        }
    }

    @Test
    fun `Should use flagProvider to retrieve flag instance`() {
        val currentValue = "5"
        val currencies = createCurrencies(1, BigDecimal.ONE)

        val result = converter.convertFor(currentValue, currencies)

        verify(mockFlagProvider).forCurrency(currencies.first().code)
    }

    private fun createCurrencies(size: Int, value: BigDecimal): List<Currency> =
        List(size) {
            Currency(randomCode(), value)
        }
}
