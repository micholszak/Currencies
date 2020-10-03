package pl.olszak.currencies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import pl.olszak.currencies.R
import pl.olszak.currencies.core.scrollToTop
import pl.olszak.currencies.presentation.CurrencyViewModel
import pl.olszak.currencies.view.adapter.CurrencyItemAdapter
import pl.olszak.currencies.view.adapter.model.CurrencyItem

@AndroidEntryPoint
class CurrenciesFragment : Fragment() {

    private val viewModel: CurrencyViewModel by viewModels()

    private lateinit var itemContainer: RecyclerView
    private lateinit var currenciesAdapter: CurrencyItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_currencies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemContainer = view.findViewById(R.id.item_container)
        currenciesAdapter = CurrencyItemAdapter(
            onValueChange = { enteredValue ->
                viewModel.onCurrencyValueChange(enteredValue)
            },
            onItemClick = { item ->
                viewModel.onCurrencyChosen(item)
                itemContainer.scrollToTop()
            }
        )
        itemContainer.layoutManager = LinearLayoutManager(context)
        itemContainer.adapter = currenciesAdapter

        viewModel.displayableItems.observe(viewLifecycleOwner, Observer(::render))
    }

    private fun render(newItems: List<CurrencyItem>) {
        currenciesAdapter.setItems(newItems)
    }
}
