package pl.olszak.currencies.core.view.model

import android.os.Bundle

interface AdapterItem {

    fun isTheSameAs(newItem: Any): Boolean

    fun hasTheSameContentAs(newItem: Any): Boolean =
        newItem == this

    fun prepareChangedPayload(newItem: Any): Bundle? = null
}
