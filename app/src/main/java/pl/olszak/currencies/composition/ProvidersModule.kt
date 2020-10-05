package pl.olszak.currencies.composition

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.olszak.currencies.presentation.model.ResourcesCurrencyNameProvider
import pl.olszak.currencies.presentation.model.provider.CurrencyNameProvider
import pl.olszak.currencies.presentation.model.provider.FlagProvider
import pl.olszak.currencies.presentation.model.provider.RemoteFlagProvider

@Module
@InstallIn(ApplicationComponent::class)
abstract class ProvidersModule {

    @Binds
    abstract fun bindFlagProvider(provider: RemoteFlagProvider): FlagProvider

    @Binds
    abstract fun bindNameProvider(provider: ResourcesCurrencyNameProvider): CurrencyNameProvider
}
