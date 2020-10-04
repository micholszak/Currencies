package pl.olszak.currencies.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.olszak.currencies.core.DebugLogger
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteCurrencyClient @Inject constructor(
    private val debugLogger: DebugLogger
) : CurrencyClient {

    companion object {
        private const val NETWORKING_TAG = "NetworkRequest"
        private const val CURRENCIES_ENDPOINT = "https://hiring.revolut.codes"
    }

    private val okHttpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            debugLogger.log(NETWORKING_TAG, message)
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        Retrofit.Builder()
            .baseUrl(CURRENCIES_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    override fun createCurrencyApi(): CurrencyApi =
        retrofit.create(CurrencyApi::class.java)
}
