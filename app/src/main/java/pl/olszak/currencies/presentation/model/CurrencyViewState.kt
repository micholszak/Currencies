package pl.olszak.currencies.presentation.model

import pl.olszak.currencies.view.adapter.model.CurrencyItem

sealed class CurrencyViewState {
    object Loading : CurrencyViewState()

    data class SuccessfulFetch(
        val displayableItems: List<CurrencyItem> = emptyList()
    ) : CurrencyViewState()

    object NetworkError : CurrencyViewState()
}
