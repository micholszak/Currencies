package pl.olszak.currencies.presentation.model

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.olszak.currencies.R
import pl.olszak.currencies.presentation.model.provider.CurrencyNameProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourcesCurrencyNameProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : CurrencyNameProvider {

    companion object {
        private val CURRENCIES: Map<String, Int> = mapOf(
            "EUR" to R.string.eur,
            "AUD" to R.string.aud,
            "BGN" to R.string.bgn,
            "BRL" to R.string.brl,
            "CAD" to R.string.cad,
            "CHF" to R.string.chf,
            "CNY" to R.string.cny,
            "CZK" to R.string.czk,
            "DKK" to R.string.dkk,
            "GBP" to R.string.gbp,
            "HKD" to R.string.hkd,
            "HRK" to R.string.hrk,
            "HUF" to R.string.huf,
            "IDR" to R.string.idr,
            "ILS" to R.string.ils,
            "INR" to R.string.inr,
            "ISK" to R.string.isk,
            "JPY" to R.string.jpy,
            "KRW" to R.string.krw,
            "MXN" to R.string.mxn,
            "MYR" to R.string.myr,
            "NOK" to R.string.nok,
            "NZD" to R.string.nzd,
            "PHP" to R.string.php,
            "PLN" to R.string.pln,
            "RON" to R.string.ron,
            "RUB" to R.string.rub,
            "SEK" to R.string.sek,
            "SGD" to R.string.sgd,
            "THB" to R.string.thb,
            "USD" to R.string.usd,
            "ZAR" to R.string.zar
        )
    }

    override fun forCode(code: String): String {
        val nameId: Int = CURRENCIES[code] ?: -1
        return if (nameId != -1) context.getString(nameId) else ""
    }
}
