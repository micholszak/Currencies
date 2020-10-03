package pl.olszak.currencies.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.olszak.currencies.core.concurrent.MapSchedulerFacade
import pl.olszak.currencies.core.concurrent.TestSchedulersProvider
import pl.olszak.currencies.core.getOrAwaitValue
import pl.olszak.currencies.domain.GetCurrenciesContinuously
import pl.olszak.currencies.domain.data.model.Currency
import pl.olszak.currencies.presentation.model.CurrencyItemConverter
import pl.olszak.currencies.remote.ConstantFlagProvider
import pl.olszak.currencies.view.adapter.model.CurrencyItem
import pl.olszak.currencies.view.adapter.model.Flag
import java.math.BigDecimal

class CurrencyViewModelTest {

    companion object {
        private const val URL = "www.some.flag.url.com"
        private const val USD = "USD"
        private const val PLN = "PLN"
        private const val EUR = "EUR"

        private val LIST = listOf(
            createCurrency(PLN),
            createCurrency(USD),
            createCurrency(EUR)
        )

        private fun createCurrency(code: String) =
            Currency(
                code = code,
                rate = BigDecimal.ONE
            )
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val schedulersProvider = TestSchedulersProvider()
    private val flagProvider = ConstantFlagProvider(URL)
    private val useCase: GetCurrenciesContinuously = mock {
        on {
            execute(
                currencyCode = anyOrNull(),
                executionIntervalSeconds = any()
            )
        } doReturn Observable.just(LIST)
    }
    private val schedulerFacade = spy(
        MapSchedulerFacade(
            schedulersProvider = schedulersProvider
        )
    )
    private val itemConverter = spy(
        CurrencyItemConverter(
            provider = flagProvider
        )
    )
    private lateinit var viewModel: CurrencyViewModel

    @Before
    fun setup() {
        viewModel = CurrencyViewModel(
            getCurrenciesContinuously = useCase,
            schedulerFacade = schedulerFacade,
            itemConverter = itemConverter
        )
    }

    @Test
    fun `Properly startup the view model`() {
        verify(schedulerFacade).unsubscribeFor(viewModel)
        verify(useCase).execute()
        verify(itemConverter).convertFor("", LIST)
    }

    @Test
    fun `Change of currency should trigger another request`() {
        triggerChangeToUSD()

        verify(useCase).execute(currencyCode = USD)
    }

    @Test
    fun `Reorder emitted list when currencies change`() {
        triggerChangeToUSD()

        val items = viewModel.displayableItems.getOrAwaitValue()
        val currencies = items.map(CurrencyItem::code)
        assertThat(currencies).isEqualTo(listOf(USD, PLN, EUR))
    }

    @Test
    fun `Recalculate emitted list when currencies change`() {
        triggerChangeToUSD("5")

        val items = viewModel.displayableItems.getOrAwaitValue()
        val first = items.first()
        (first).run {
            assertThat(code).isEqualTo(USD)
            assertThat(amount).isEqualTo("5")
        }

        val filteredList = items.subList(1, items.size)
        filteredList.forEach { item ->
            assertThat(item.amount).isEqualTo("5.00")
        }
    }

    @Test
    fun `Recalculate values for items on value change`() {
        viewModel.onCurrencyValueChange("5")
        val items = viewModel.displayableItems.getOrAwaitValue()

        val first = items.first()
        (first).run {
            assertThat(code).isEqualTo(PLN)
            assertThat(amount).isEqualTo("5")
        }

        val filteredList = items.subList(1, items.size)
        filteredList.forEach { item ->
            assertThat(item.amount).isEqualTo("5.00")
        }
    }

    private fun triggerChangeToUSD(currencyAmount: String = "1") {
        viewModel.onCurrencyChosen(
            CurrencyItem(
                code = USD,
                flag = Flag(),
                amount = currencyAmount
            )
        )
    }
}
