package pl.olszak.currencies.presentation.model.provider

interface CurrencyNameProvider {

    fun forCode(code: String): String
}
