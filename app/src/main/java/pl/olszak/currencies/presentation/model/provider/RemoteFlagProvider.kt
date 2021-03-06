package pl.olszak.currencies.presentation.model.provider

import pl.olszak.currencies.view.adapter.model.Flag
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteFlagProvider @Inject constructor() : FlagProvider {

    companion object {
        private const val ISO_4217_CODE_LENGTH = 3
    }

    override fun forCurrency(currencyCode: String): Flag {
        val url = if (isValidCurrencyCode(currencyCode)) {
            val countryCode = currencyCode.take(2)
            "https://www.countryflags.io/$countryCode/flat/64.png"
        } else ""
        return Flag(url)
    }

    private fun isValidCurrencyCode(code: String): Boolean =
        code.length == ISO_4217_CODE_LENGTH &&
            code.none { character ->
                character.isLetter().not()
            }
}
