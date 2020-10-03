package pl.olszak.currencies.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.olszak.currencies.core.concurrent.SchedulerFacade
import pl.olszak.currencies.domain.GetCurrenciesContinuously
import pl.olszak.currencies.domain.data.model.Currency
import pl.olszak.currencies.presentation.model.CurrencyItemConverter
import pl.olszak.currencies.presentation.model.CurrencyState
import pl.olszak.currencies.view.adapter.model.CurrencyItem

class CurrencyViewModel @ViewModelInject constructor(
    private val getCurrenciesContinuously: GetCurrenciesContinuously,
    private val schedulerFacade: SchedulerFacade,
    private val itemConverter: CurrencyItemConverter
) : ViewModel() {

    private val mutableItems: MutableLiveData<List<CurrencyItem>> =
        MutableLiveData(emptyList())
    val displayableItems: LiveData<List<CurrencyItem>> = mutableItems

    private var state = CurrencyState()

    init {
        requestForList()
    }

    fun onCurrencyChosen(item: CurrencyItem) {
        state = state.copy(
            enteredValue = item.amount,
            currentList = state.currentList.reorderFor(item.code)
        )
        populateAdapter()
        requestForList(code = item.code)
    }

    fun onCurrencyValueChange(enteredValue: String) {
        state = state.copy(enteredValue = enteredValue)
        populateAdapter()
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
            }
        )
    }

    private fun populateAdapter() {
        val adapterItems: List<CurrencyItem> =
            itemConverter.convertFor(
                currentValue = state.enteredValue,
                currencies = state.currentList
            )
        mutableItems.postValue(adapterItems)
    }

    private fun populateStateWith(currencies: List<Currency>) {
        val newList: List<Currency> = if (state.currentList.isEmpty()) {
            currencies
        } else {
            val currentOrder = state.currentList.map(Currency::code)
            val currenciesMap = currencies.associateBy(Currency::code)
            currentOrder.mapNotNull { code -> currenciesMap[code] }
        }
        state = state.copy(
            currentList = newList
        )
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
