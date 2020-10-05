package pl.olszak.currencies.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.olszak.currencies.core.concurrent.SchedulerFacade
import pl.olszak.currencies.domain.GetCurrenciesContinuously
import pl.olszak.currencies.domain.data.model.Currency
import pl.olszak.currencies.presentation.model.CurrencyState
import pl.olszak.currencies.presentation.model.CurrencyViewState
import pl.olszak.currencies.presentation.model.provider.CurrencyItemConverter
import pl.olszak.currencies.view.adapter.model.CurrencyItem

class CurrencyViewModel @ViewModelInject constructor(
    private val getCurrenciesContinuously: GetCurrenciesContinuously,
    private val schedulerFacade: SchedulerFacade,
    private val itemConverter: CurrencyItemConverter
) : ViewModel() {

    private val mutableViewState: MutableLiveData<CurrencyViewState> =
        MutableLiveData(CurrencyViewState.Loading)
    val viewState: LiveData<CurrencyViewState> = mutableViewState

    private var viewModelState = CurrencyState()

    fun fetchCurrencies() {
        requestForList()
    }

    fun onCurrencyChosen(item: CurrencyItem) {
        viewModelState = viewModelState.copy(
            enteredValue = item.amount,
            currentList = viewModelState.currentList.reorderFor(item.code)
        )
        populateAdapter()
        requestForList(code = item.code)
    }

    fun onCurrencyValueChange(enteredValue: String) {
        viewModelState = viewModelState.copy(enteredValue = enteredValue)
        populateAdapter()
    }

    fun tryRefresh() {
        postLoading()
        requestForList()
    }

    override fun onCleared() {
        clearSubscriptions()
    }

    private fun requestForList(code: String? = null) {
        clearSubscriptions()
        val currencyRequest = getCurrenciesContinuously.execute(currencyCode = code)
        schedulerFacade.subscribe(
            subscriber = this,
            source = currencyRequest,
            onNext = { currencies ->
                populateStateWith(currencies)
                populateAdapter()
            },
            onError = {
                postError()
            }
        )
    }

    private fun populateAdapter() {
        val adapterItems: List<CurrencyItem> =
            itemConverter.convertFor(
                currentValue = viewModelState.enteredValue,
                currencies = viewModelState.currentList
            )
        mutableViewState.postValue(
            CurrencyViewState.SuccessfulFetch(
                displayableItems = adapterItems
            )
        )
    }

    private fun populateStateWith(currencies: List<Currency>) {
        val newList: List<Currency> = if (viewModelState.currentList.isEmpty()) {
            currencies
        } else {
            val currentOrder = viewModelState.currentList.map(Currency::code)
            val currenciesMap = currencies.associateBy(Currency::code)
            currentOrder.mapNotNull { code -> currenciesMap[code] }
        }
        viewModelState = viewModelState.copy(
            currentList = newList
        )
    }

    private fun postError() {
        mutableViewState.postValue(CurrencyViewState.NetworkError)
    }

    private fun postLoading() {
        mutableViewState.postValue(CurrencyViewState.Loading)
    }

    private fun clearSubscriptions() {
        schedulerFacade.unsubscribeFor(this)
    }

    private fun List<Currency>.reorderFor(code: String): List<Currency> {
        val currency = first { currency -> currency.code == code }
        val newList = toMutableList()
        newList.remove(currency)
        newList.add(0, currency)
        return newList
    }
}
