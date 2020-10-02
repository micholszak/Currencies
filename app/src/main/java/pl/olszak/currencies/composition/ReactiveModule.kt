package pl.olszak.currencies.composition

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.olszak.currencies.core.concurrent.ApplicationSchedulersProvider
import pl.olszak.currencies.core.concurrent.MapSchedulerFacade
import pl.olszak.currencies.core.concurrent.SchedulerFacade
import pl.olszak.currencies.core.concurrent.SchedulersProvider

@Module
@InstallIn(ApplicationComponent::class)
abstract class ReactiveModule {

    @Binds
    abstract fun bindSchedulersProvider(provider: ApplicationSchedulersProvider): SchedulersProvider

    @Binds
    abstract fun bindSchedulerFacade(facade: MapSchedulerFacade): SchedulerFacade
}
