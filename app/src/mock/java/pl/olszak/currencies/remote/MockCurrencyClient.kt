package pl.olszak.currencies.remote

import io.reactivex.rxjava3.core.Single
import pl.olszak.currencies.remote.model.CurrenciesResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockCurrencyClient @Inject constructor() : CurrencyClient {

    override fun createCurrencyApi(): CurrencyApi =
        object : CurrencyApi {

            override fun getCurrencies(currency: String?): Single<CurrenciesResponse> =
                Single.just(CurrenciesResponse())
        }
}
