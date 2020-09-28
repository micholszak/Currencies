package pl.olszak.currencies.utils

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmptyLogger @Inject constructor() : DebugLogger {

    override fun log(tag: String, message: String) {
    }
}
