package pl.olszak.currencies.core

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val FIRST_POSITION = 0
private const val OFFSET = 1

fun View.showSoftInputMethod() {
    val inputMethodManager: InputMethodManager? = context.inputMethodManager
    inputMethodManager?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideSoftInputMethod() {
    val inputMethodManager: InputMethodManager? = context.inputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun RecyclerView.scrollToTop() {
    val linearLayoutManager: LinearLayoutManager? = layoutManager as? LinearLayoutManager
    linearLayoutManager?.scrollToPositionWithOffset(FIRST_POSITION, OFFSET)
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

private val Context.inputMethodManager: InputMethodManager?
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
