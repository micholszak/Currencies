package pl.olszak.currencies.presentation.model.provider

import pl.olszak.currencies.view.adapter.model.Flag

interface FlagProvider {

    fun forCurrency(currencyCode: String): Flag
}
