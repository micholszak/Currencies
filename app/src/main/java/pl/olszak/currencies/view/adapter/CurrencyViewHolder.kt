package pl.olszak.currencies.view.adapter

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import coil.load
import pl.olszak.currencies.R
import pl.olszak.currencies.core.hideSoftInputMethod
import pl.olszak.currencies.core.showSoftInputMethod
import pl.olszak.currencies.core.view.adapter.BaseViewHolder
import pl.olszak.currencies.view.adapter.model.CurrencyItem

class CurrencyViewHolder(
    view: View
) : BaseViewHolder<CurrencyItem>(view) {

    var onValueChange: (String) -> Unit = {}
    var onClick: (item: CurrencyItem) -> Unit = {}

    private val currencyCodeText: TextView = itemView.findViewById(R.id.currency__code_text)
    private val flagImage: ImageView = itemView.findViewById(R.id.flag_image)
    private val currencyEdit: EditText = itemView.findViewById(R.id.currency_edit)

    override fun bind(item: CurrencyItem) {
        currencyCodeText.text = item.code
        flagImage.load(item.flag.url)
        setupEdit(item.amount)
        setupOnClickListener(item)
    }

    override fun update(bundle: Bundle) {
        if (bundle.containsKey(CurrencyItem.AMOUNT_KEY)) {
            val newAmount = bundle.getString(CurrencyItem.AMOUNT_KEY, "")
            setupEdit(newAmount)
        }
    }

    private fun setupEdit(amount: String) {
        with(currencyEdit) {
            if (hasFocus()) return
            setText(amount)
            doAfterTextChanged { text ->
                if (hasFocus()) {
                    onValueChange(text?.toString().orEmpty())
                }
            }
        }
    }

    private fun setupOnClickListener(item: CurrencyItem) {
        itemView.setOnClickListener {
            onClick(item)
            currencyEdit.requestFocus()
        }
        currencyEdit.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                onClick(item)
                currencyEdit.setSelection(item.amount.length)
                view.showSoftInputMethod()
            } else {
                view.hideSoftInputMethod()
            }
        }
    }
}
