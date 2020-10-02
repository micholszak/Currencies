package pl.olszak.currencies.domain.data

import io.reactivex.rxjava3.core.Single
import pl.olszak.currencies.domain.data.model.Currency
import pl.olszak.currencies.domain.data.model.CurrencyConverter
import pl.olszak.currencies.remote.CurrencyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyServiceFacade @Inject constructor(
    private val currencyApi: CurrencyApi,
    private val converter: CurrencyConverter
) : CurrencyService {

    override fun getCurrencies(currencyCode: String?): Single<List<Currency>> =
        currencyApi.getCurrencies(currencyCode)
            .map(converter::convert)
}
