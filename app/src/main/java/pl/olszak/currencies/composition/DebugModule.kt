package pl.olszak.currencies.composition

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.olszak.currencies.utils.DebugLogger
import pl.olszak.currencies.utils.LogCatLogger

@Module
@InstallIn(ApplicationComponent::class)
abstract class DebugModule {

    @Binds
    abstract fun bindLogger(logCatLogger: LogCatLogger): DebugLogger
}
