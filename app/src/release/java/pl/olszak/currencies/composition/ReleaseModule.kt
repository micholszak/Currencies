package pl.olszak.currencies.composition

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.olszak.currencies.utils.DebugLogger
import pl.olszak.currencies.utils.EmptyLogger

@Module
@InstallIn(ApplicationComponent::class)
abstract class ReleaseModule {

    @Binds
    abstract fun bindsDebugLogger(emptyLogger: EmptyLogger): DebugLogger
}
