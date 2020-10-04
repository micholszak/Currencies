package pl.olszak.currencies.composition

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.olszak.currencies.remote.CurrencyClient
import pl.olszak.currencies.remote.RemoteCurrencyClient

@Module
@InstallIn(ApplicationComponent::class)
abstract class ProductionModule {

    @Binds
    abstract fun bindCurrencyClient(client: RemoteCurrencyClient): CurrencyClient
}
