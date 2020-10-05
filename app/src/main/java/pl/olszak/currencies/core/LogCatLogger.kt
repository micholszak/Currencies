package pl.olszak.currencies.core

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogCatLogger @Inject constructor() : DebugLogger {

    override fun log(tag: String, message: String) {
        Log.d(tag, message)
    }
}
