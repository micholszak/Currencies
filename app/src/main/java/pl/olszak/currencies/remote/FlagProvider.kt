package pl.olszak.currencies.remote

object FlagProvider {

    private const val ISO_4217_CODE_LENGTH = 3

    fun forCurrency(currencyCode: String): String {
        if (isValidCurrencyCode(currencyCode)) {
            val countryCode = currencyCode.take(2)
            return "https://www.countryflags.io/$countryCode/flat/64.png"
        }
        return ""
    }

    private fun isValidCurrencyCode(code: String): Boolean =
        code.length == ISO_4217_CODE_LENGTH &&
            code.none { character ->
                character.isLetter().not()
            }
}
