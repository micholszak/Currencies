package pl.olszak.currencies.composition

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.olszak.currencies.remote.CurrencyClient
import pl.olszak.currencies.remote.MockCurrencyClient

@Module
@InstallIn(ApplicationComponent::class)
abstract class MockModule {

    @Binds
    abstract fun bindCurrencyClient(client: MockCurrencyClient): CurrencyClient
}
