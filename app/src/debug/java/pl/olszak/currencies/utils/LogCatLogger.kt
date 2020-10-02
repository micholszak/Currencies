package pl.olszak.currencies.utils

import android.util.Log
import pl.olszak.currencies.core.DebugLogger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogCatLogger @Inject constructor() : DebugLogger {

    override fun log(tag: String, message: String) {
        Log.d(tag, message)
    }
}
