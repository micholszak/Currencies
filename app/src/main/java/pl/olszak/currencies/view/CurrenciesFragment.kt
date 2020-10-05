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
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import pl.olszak.currencies.R
import pl.olszak.currencies.core.hide
import pl.olszak.currencies.core.scrollToTop
import pl.olszak.currencies.core.show
import pl.olszak.currencies.presentation.CurrencyViewModel
import pl.olszak.currencies.presentation.model.CurrencyViewState
import pl.olszak.currencies.view.adapter.CurrencyItemAdapter

@AndroidEntryPoint
class CurrenciesFragment : Fragment() {

    companion object {
        private const val FADE_IN_ANIMATION_DURATION = 200L
    }

    private val viewModel: CurrencyViewModel by viewModels()

    private lateinit var itemContainer: RecyclerView
    private lateinit var currenciesAdapter: CurrencyItemAdapter
    private lateinit var errorContainer: View
    private lateinit var connectionError: View
    private lateinit var loadingContainer: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_currencies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemContainer = view.findViewById(R.id.item_container)
        errorContainer = view.findViewById(R.id.error_container)
        connectionError = view.findViewById(R.id.connection_error)
        loadingContainer = view.findViewById(R.id.loading_container)

        setupItemContainer()
        connectionError.setOnClickListener {
            viewModel.tryRefresh()
        }
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchCurrencies()
    }

    private fun setupItemContainer() {
        currenciesAdapter = CurrencyItemAdapter(
            onValueChange = { enteredValue ->
                viewModel.onCurrencyValueChange(enteredValue)
            },
            onItemClick = { item ->
                viewModel.onCurrencyChosen(item)
                itemContainer.scrollToTop()
            }
        )

        itemContainer.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = currenciesAdapter
            itemAnimator = FadeInUpAnimator().apply {
                addDuration = FADE_IN_ANIMATION_DURATION
            }
        }
    }

    private fun render(viewState: CurrencyViewState) {
        when (viewState) {
            is CurrencyViewState.Loading -> {
                errorContainer.hide()
                itemContainer.hide()

                loadingContainer.show()
            }
            is CurrencyViewState.NetworkError -> {
                itemContainer.hide()
                loadingContainer.hide()

                errorContainer.show()
            }
            is CurrencyViewState.SuccessfulFetch -> {
                loadingContainer.hide()
                errorContainer.hide()

                itemContainer.show()
                currenciesAdapter.setItems(viewState.displayableItems)
            }
        }
    }
}
