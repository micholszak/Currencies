package pl.olszak.currencies.domain

import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Test
import pl.olszak.currencies.core.concurrent.TestSchedulerProvider
import pl.olszak.currencies.domain.data.CurrencyService
import java.util.concurrent.TimeUnit

class GetCurrenciesContinuouslyTest {

    private val mockService: CurrencyService = mock {
        on { getCurrencies(anyOrNull()) } doReturn Single.just(emptyList())
    }
    private val testScheduler = TestScheduler()
    private val testSchedulerProvider = TestSchedulerProvider(computation = testScheduler)

    private val useCase = GetCurrenciesContinuously(
        service = mockService,
        schedulersProvider = testSchedulerProvider
    )

    @Test
    fun `Request instantly for currency list`() {
        useCase.execute().test()
        testScheduler.triggerActions()

        verify(mockService).getCurrencies(null)
    }

    @Test
    fun `Schedule continuously given task`() {
        useCase.execute(executionIntervalSeconds = 2L).test()
        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS)

        verify(mockService, times(6)).getCurrencies(null)
    }
}
