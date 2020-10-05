package pl.olszak.currencies.composition

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.Multibinds
import pl.olszak.currencies.core.DebugLogger
import pl.olszak.currencies.core.DebugLoggerDelegate

@Module
@InstallIn(ApplicationComponent::class)
abstract class CoreComponentsModule {

    @Multibinds
    abstract fun debugLoggersSet(): Set<DebugLogger>

    @Binds
    abstract fun bindDebugLoggerDelegate(delegate: DebugLoggerDelegate): DebugLogger
}
