package pl.olszak.currencies.core.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(
    parent: ViewGroup,
    @LayoutRes layoutId: Int
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
) {

    abstract fun bind(item: T)

    abstract fun update(bundle: Bundle)

    fun onUpdate(payload: Any) {
        val bundle = payload as? Bundle
        if (bundle != null) {
            update(bundle)
        }
    }
}
