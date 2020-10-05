package pl.olszak.currencies.composition

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoSet
import pl.olszak.currencies.core.DebugLogger
import pl.olszak.currencies.core.LogCatLogger

@Module
@InstallIn(ApplicationComponent::class)
abstract class DebugModule {

    @Binds
    @IntoSet
    abstract fun bindLogger(logCatLogger: LogCatLogger): DebugLogger
}
