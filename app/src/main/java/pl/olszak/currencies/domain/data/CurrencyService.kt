package pl.olszak.currencies.domain.data

import io.reactivex.rxjava3.core.Single
import pl.olszak.currencies.domain.data.model.Currency

interface CurrencyService {

    fun getCurrencies(currencyCode: String? = null): Single<List<Currency>>
}
