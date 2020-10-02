package pl.olszak.currencies.presentation.model

import pl.olszak.currencies.domain.data.model.Currency

data class CurrencyState(
    val enteredValue: String = "",
    val currentList: List<Currency> = emptyList(),
)
