package pl.olszak.currencies.presentation.model.provider

import pl.olszak.currencies.core.formatToString
import pl.olszak.currencies.core.toBigDecimal
import pl.olszak.currencies.domain.data.model.Currency
import pl.olszak.currencies.view.adapter.model.CurrencyItem
import java.math.BigDecimal
import javax.inject.Inject

class CurrencyItemConverter @Inject constructor(
    private val flagProvider: FlagProvider,
    private val nameProvider: CurrencyNameProvider
) {

    companion object {
        private val DEFAULT_VALUE = BigDecimal.ZERO
    }

    fun convertFor(currentValue: String, currencies: List<Currency>): List<CurrencyItem> =
        currencies.mapIndexed { index, currency ->
            val amount: String = when {
                (index == 0) -> currentValue
                (currentValue.isNotEmpty()) -> recalculateAmount(currentValue, currency)
                else -> ""
            }
            CurrencyItem(
                code = currency.code,
                name = nameProvider.forCode(currency.code),
                flag = flagProvider.forCurrency(currency.code),
                amount = amount
            )
        }

    private fun recalculateAmount(currentValue: String, currency: Currency): String {
        val currentDecimal = currentValue.toBigDecimal(DEFAULT_VALUE)
        val newAmount = currency.rate * currentDecimal
        return newAmount.formatToString()
    }
}
