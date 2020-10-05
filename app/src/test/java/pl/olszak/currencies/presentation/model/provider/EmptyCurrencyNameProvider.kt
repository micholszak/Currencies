package pl.olszak.currencies.presentation.model.provider

object EmptyCurrencyNameProvider : CurrencyNameProvider {

    override fun forCode(code: String): String = ""
}
