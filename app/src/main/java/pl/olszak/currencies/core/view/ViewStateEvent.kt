package pl.olszak.currencies.core.view

/**
 * Representation of single shot event to handle by view
 */
data class ViewStateEvent<T>(private val payload: T? = null) {
    private var isConsumed = false

    fun consume(action: (T?) -> Unit) {
        if (isConsumed.not()) {
            action(payload)
            isConsumed = true
        }
    }
}
