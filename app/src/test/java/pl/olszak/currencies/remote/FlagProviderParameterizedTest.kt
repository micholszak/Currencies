package pl.olszak.currencies.remote

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class FlagProviderParameterizedTest(
    private val givenCurrencyCode: String,
    private val expectedResult: String
) {

    companion object {
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
                    expectedResult = ""
                ),
                testCase(
                    givenCurrencyCode = "",
                    expectedResult = ""
                ),
                testCase(
                    givenCurrencyCode = "PL",
                    expectedResult = ""
                )
            )

        private fun testCase(givenCurrencyCode: String, expectedResult: String): Array<Any> =
            arrayOf(givenCurrencyCode, expectedResult)

        private fun flagWebsite(code: String) =
            "https://www.countryflags.io/$code/flat/64.png"
    }

    @Test
    fun `Properly convert currency code into flag url`() {
        val result = FlagProvider.forCurrency(givenCurrencyCode)

        assertThat(result).isEqualTo(expectedResult)
    }
}
