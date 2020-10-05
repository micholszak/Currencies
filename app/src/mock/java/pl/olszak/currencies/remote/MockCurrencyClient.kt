package pl.olszak.currencies.remote

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockCurrencyClient @Inject constructor() : CurrencyClient {

    override fun createCurrencyApi(): CurrencyApi = MockCurrencyApi()
}
