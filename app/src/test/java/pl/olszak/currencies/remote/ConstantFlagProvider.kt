package pl.olszak.currencies.remote

import pl.olszak.currencies.view.adapter.model.Flag

class ConstantFlagProvider(private val url: String) : FlagProvider {
    override fun forCurrency(currencyCode: String): Flag =
        Flag(url)
}
