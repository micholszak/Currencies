package pl.olszak.currencies.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.olszak.currencies.R
import pl.olszak.currencies.core.view.SimpleDiffUtilCallback
import pl.olszak.currencies.view.adapter.model.CurrencyItem

class CurrencyItemAdapter(
    private val onValueChange: (String) -> Unit,
    private val onItemClick: (item: CurrencyItem) -> Unit
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    private val items: MutableList<CurrencyItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.currency_list_item, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(items[position])
        holder.onClick = onItemClick
        holder.onValueChange = onValueChange
    }

    override fun getItemCount(): Int =
        items.size

    fun setItems(newItems: List<CurrencyItem>) {
        val callback = SimpleDiffUtilCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
        items.clear()
        items.addAll(newItems)
    }
}
