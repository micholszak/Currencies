package pl.olszak.currencies.core.view.adapter

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T)

    abstract fun update(bundle: Bundle)

    fun onUpdate(payload: Any) {
        val bundle = payload as? Bundle
        if (bundle != null) {
            update(bundle)
        }
    }
}
