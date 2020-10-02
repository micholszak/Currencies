package pl.olszak.currencies.core.view

import androidx.recyclerview.widget.DiffUtil
import pl.olszak.currencies.core.view.model.AdapterItem

class SimpleDiffUtilCallback(
    private val oldList: List<AdapterItem>,
    private val newList: List<AdapterItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int =
        oldList.size

    override fun getNewListSize(): Int =
        newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].isTheSameAs(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].hasTheSameContentAs(newList[newItemPosition])

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? =
        oldList[oldItemPosition].prepareChangedPayload(newList[newItemPosition])
}
