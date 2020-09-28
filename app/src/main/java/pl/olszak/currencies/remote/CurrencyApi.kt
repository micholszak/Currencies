package pl.olszak.currencies.remote

import io.reactivex.rxjava3.core.Single
import pl.olszak.currencies.remote.model.CurrenciesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/api/android/latest")
    fun getCurrencies(@Query("base") currency: String?): Single<CurrenciesResponse>
}
