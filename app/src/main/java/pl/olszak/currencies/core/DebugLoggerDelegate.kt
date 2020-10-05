package pl.olszak.currencies.core

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DebugLoggerDelegate @Inject constructor(
    private val loggers: Set<@JvmSuppressWildcards DebugLogger>
) : DebugLogger {

    override fun log(tag: String, message: String) =
        loggers.forEach { logger ->
            logger.log(tag, message)
        }
}
