package pl.olszak.currencies.remote

import pl.olszak.currencies.view.adapter.model.Flag

interface FlagProvider {

    fun forCurrency(currencyCode: String): Flag
}
