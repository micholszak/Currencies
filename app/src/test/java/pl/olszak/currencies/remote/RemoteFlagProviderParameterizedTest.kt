package pl.olszak.currencies.remote

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import pl.olszak.currencies.view.adapter.model.Flag

@RunWith(Parameterized::class)
class RemoteFlagProviderParameterizedTest(
    private val givenCurrencyCode: String,
    private val expectedResult: Flag
) {

    companion object {
        private val EMPTY_FLAG = Flag()

        @JvmStatic
        @Parameterized.Parameters(name = "{0} to {1}")
        fun parameters(): Collection<Array<Any>> =
            listOf(
                testCase(
                    givenCurrencyCode = "USD",
                    expectedResult = flagWebsite("US")
                ),
                testCase(
                    givenCurrencyCode = "EUR",
                    expectedResult = flagWebsite("EU")
                ),
                testCase(
                    givenCurrencyCode = "PLN",
                    expectedResult = flagWebsite("PL")
                ),
                testCase(
                    givenCurrencyCode = "Polska",
                    expectedResult = EMPTY_FLAG
                ),
                testCase(
                    givenCurrencyCode = "",
                    expectedResult = EMPTY_FLAG
                ),
                testCase(
                    givenCurrencyCode = "PL",
                    expectedResult = EMPTY_FLAG
                )
            )

        private fun testCase(givenCurrencyCode: String, expectedResult: Flag): Array<Any> =
            arrayOf(givenCurrencyCode, expectedResult)

        private fun flagWebsite(code: String) =
            Flag("https://www.countryflags.io/$code/flat/64.png")
    }

    private val provider = RemoteFlagProvider()

    @Test
    fun `Properly convert currency code into flag url`() {
        val result = provider.forCurrency(givenCurrencyCode)

        assertThat(result).isEqualTo(expectedResult)
    }
}
