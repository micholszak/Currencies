package pl.olszak.currencies.domain

import io.reactivex.rxjava3.core.Observable
import pl.olszak.currencies.core.concurrent.SchedulersProvider
import pl.olszak.currencies.domain.data.CurrencyService
import pl.olszak.currencies.domain.data.model.Currency
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetCurrenciesContinuously @Inject constructor(
    private val service: CurrencyService,
    private val schedulersProvider: SchedulersProvider
) {

    companion object {
        private const val REQUEST_INTERVAL_SECONDS = 1L
        private const val INITIAL_DELAY = 0L
    }

    fun execute(
        currencyCode: String? = null,
        executionIntervalSeconds: Long = REQUEST_INTERVAL_SECONDS
    ): Observable<List<Currency>> =
        Observable.interval(
            INITIAL_DELAY,
            executionIntervalSeconds,
            TimeUnit.SECONDS,
            schedulersProvider.computation()
        ).flatMap {
            service.getCurrencies(currencyCode)
                .toObservable()
        }
}
