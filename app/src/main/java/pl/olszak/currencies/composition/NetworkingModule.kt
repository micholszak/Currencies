package pl.olszak.currencies.composition

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.olszak.currencies.core.DebugLogger
import pl.olszak.currencies.remote.CurrencyApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkingModule {

    companion object {
        private const val NETWORKING_TAG = "NetworkRequest"
        private const val CURRENCIES_ENDPOINT = "https://hiring.revolut.codes"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(debugLogger: DebugLogger): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            debugLogger.log(NETWORKING_TAG, message)
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyApi(client: OkHttpClient): CurrencyApi =
        createService(
            url = CURRENCIES_ENDPOINT,
            client = client
        )

    private inline fun <reified T> createService(url: String, client: OkHttpClient): T {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        return retrofit.create(T::class.java)
    }
}
