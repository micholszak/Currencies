package pl.olszak.currencies.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import pl.olszak.currencies.R
import pl.olszak.currencies.core.scrollToTop
import pl.olszak.currencies.presentation.CurrencyViewModel
import pl.olszak.currencies.view.adapter.CurrencyItemAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val currencyViewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val itemContainer: RecyclerView = findViewById(R.id.item_container)
        val adapter = CurrencyItemAdapter(
            onValueChange = { currentValue ->
                currencyViewModel.onCurrencyValueChange(currentValue)
            },
            onItemClick = { item ->
                currencyViewModel.onCurrencyChosen(item)
                itemContainer.scrollToTop()
            }
        )
        itemContainer.layoutManager = LinearLayoutManager(this)
        itemContainer.adapter = adapter

        currencyViewModel.displayableItems.observe(this) { newItems ->
            adapter.setItems(newItems)
        }
    }
}
