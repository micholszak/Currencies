package pl.olszak.currencies.composition

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.olszak.currencies.domain.data.CurrencyService
import pl.olszak.currencies.domain.data.CurrencyServiceFacade

@Module
@InstallIn(ApplicationComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun bindCurrencyService(facade: CurrencyServiceFacade): CurrencyService
}
