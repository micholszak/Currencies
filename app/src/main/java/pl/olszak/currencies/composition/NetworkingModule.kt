package pl.olszak.currencies.composition

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.olszak.currencies.remote.CurrencyApi
import pl.olszak.currencies.remote.CurrencyClient

@Module
@InstallIn(ApplicationComponent::class)
class NetworkingModule {

    @Provides
    fun provideCurrencyApi(client: CurrencyClient): CurrencyApi =
        client.createCurrencyApi()
}
