package pl.olszak.currencies.view.adapter.model

import android.os.Bundle
import androidx.core.os.bundleOf
import pl.olszak.currencies.core.view.model.AdapterItem

data class CurrencyItem(
    val code: String,
    val flag: Flag,
    val amount: String
) : AdapterItem {

    companion object {
        const val AMOUNT_KEY = "currency_item_amount_key"
    }

    override fun isTheSameAs(newItem: Any): Boolean =
        (newItem as? CurrencyItem)?.code == code

    override fun prepareChangedPayload(newItem: Any): Bundle? {
        val item = newItem as? CurrencyItem
        if (item != null) {
            if (item.amount != amount) {
                return bundleOf(AMOUNT_KEY to item.amount)
            }
        }
        return null
    }
}
