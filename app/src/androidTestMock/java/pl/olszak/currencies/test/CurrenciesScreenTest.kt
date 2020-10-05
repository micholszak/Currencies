package pl.olszak.currencies.test

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import pl.olszak.currencies.MainActivity
import pl.olszak.currencies.robot.CurrenciesScreenRobot
import pl.olszak.currencies.robot.DeviceRobot
import pl.olszak.currencies.robot.model.CurrencyValues

@RunWith(JUnit4::class)
class CurrenciesScreenTest : TestCase() {

    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    private val screenRobot = CurrenciesScreenRobot()
    private val deviceRobot = DeviceRobot()

    @Test
    fun errorVisibleOnConnectionProblems() =
        before("Error state should be visible when error occurs from api") {
            deviceRobot.disableInternetConnection()
        }.after {
            deviceRobot.enableInternetConnection()
        }.run {
            step("Check that error is visible") {
                screenRobot.assertErrorStateIsVisible()
            }
        }

    @Test
    fun reloadDataAfterErrorPromptClick() {
        before("Clicking on error prompt should attempt to clear error state") {
            deviceRobot.disableInternetConnection()
        }.after {
            deviceRobot.enableInternetConnection()
        }.run {
            step("Visible error state") {
                screenRobot.assertErrorStateIsVisible()
            }
            step("Internet connection is turned on") {
                deviceRobot.enableInternetConnection()
            }
            step("Click on error prompt") {
                screenRobot.clickOnErrorPrompt()
            }
            step("Error prompt should hide") {
                screenRobot.assertErrorStateIsGone()
            }
            step("Item container should be visible") {
                screenRobot.assertItemContainerIsVisible()
            }
        }
    }

    @Test
    fun listIsDisplayedProperly() {
        run("Items should be properly displayed") {
            step("Fully loaded list contains 6 items") {
                screenRobot.assertThatListContainsItems(6)
            }
            step("Every view in row is visible") {
                screenRobot.assertEveryRowsItemVisible()
            }
            step("Views contain values mapped from feed") {
                screenRobot.assertRowContainsValues(
                    position = 0,
                    CurrencyValues(
                        code = "EUR",
                        name = "Euro",
                    )
                )
                screenRobot.assertRowContainsValues(
                    position = 1,
                    CurrencyValues(
                        code = "AUD",
                        name = "Australian Dollar"
                    )
                )
                screenRobot.assertRowContainsValues(
                    position = 2,
                    CurrencyValues(
                        code = "BRL",
                        name = "Brazil Real"
                    )
                )
                screenRobot.assertRowContainsValues(
                    position = 3,
                    CurrencyValues(
                        code = "CAD",
                        name = "Canada Dollar"
                    )
                )
                screenRobot.assertRowContainsValues(
                    position = 4,
                    CurrencyValues(
                        code = "PLN",
                        name = "Poland Zloty"
                    )
                )
                screenRobot.assertRowContainsValues(
                    position = 5,
                    CurrencyValues(
                        code = "USD",
                        name = "United States Dollar"
                    )
                )
            }
        }
    }

    @Test
    fun clickingAnItemBringsItToTheTop() {
        run("Clicking an item brings it to the top") {
            step("Perform click on an PLN rate") {
                screenRobot.performClickOnRow(position = 4)
            }
            step("PLN should be at the top of the list") {
                screenRobot.assertRowContainsValues(
                    position = 0,
                    CurrencyValues(
                        code = "PLN",
                        name = "Poland Zloty"
                    )
                )
            }
            step("List should be properly ordered") {
                val orderedCodes = listOf("PLN", "EUR", "AUD", "BRL", "CAD", "USD")
                screenRobot.assertRowOrder(codes = orderedCodes)
            }
        }
    }
}
