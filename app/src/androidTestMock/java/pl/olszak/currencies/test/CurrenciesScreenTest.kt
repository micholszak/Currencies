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
                val valuesList: List<CurrencyValues> = listOf(
                    CurrencyValues(
                        code = "EUR",
                        name = "Euro",
                    ),
                    CurrencyValues(
                        code = "AUD",
                        name = "Australian Dollar"
                    ),
                    CurrencyValues(
                        code = "BRL",
                        name = "Brazil Real"
                    ),
                    CurrencyValues(
                        code = "CAD",
                        name = "Canada Dollar"
                    ),
                    CurrencyValues(
                        code = "PLN",
                        name = "Poland Zloty"
                    ),
                    CurrencyValues(
                        code = "USD",
                        name = "United States Dollar"
                    )
                )
                screenRobot.assertListContainsValues(valuesList = valuesList)
            }
        }
    }

    @Test
    fun clickingAnItemBringsItToTheTop() {
        run("Clicking an item brings it to the top of the list") {
            step("Perform click on an PLN rate") {
                screenRobot.clickOnRow(position = 4)
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

    @Test
    fun editingAnItemBringItToTheTop() {
        run("Edition of item bring it to the top of the list") {
            step("Perform click on an PLN edit") {
                screenRobot.clickOnRowsEdit(position = 4)
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

    @Test
    fun editAnItemToCalculateCurrencies() {
        run("Edition of item triggers recalculation of other rates") {
            step("Edit PLN value") {
                screenRobot.editValueOfRow(position = 4, value = "5")
            }
            step("List should refresh according to entered value") {
                val valuesList: List<CurrencyValues> = listOf(
                    CurrencyValues(
                        code = "PLN",
                        name = "Poland Zloty",
                        value = "5"
                    ),
                    CurrencyValues(
                        code = "EUR",
                        name = "Euro",
                        value = "5.00"
                    ),
                    CurrencyValues(
                        code = "AUD",
                        name = "Australian Dollar",
                        value = "5.00"
                    ),
                    CurrencyValues(
                        code = "BRL",
                        name = "Brazil Real",
                        value = "5.00"
                    ),
                    CurrencyValues(
                        code = "CAD",
                        name = "Canada Dollar",
                        value = "5.00"
                    ),
                    CurrencyValues(
                        code = "USD",
                        name = "United States Dollar",
                        value = "5.00"
                    )
                )
                screenRobot.assertListContainsValues(valuesList = valuesList)
            }
        }
    }

    @Test
    fun valueIsCopiedToClickedItem() {
        run("Previously entered value is copied to clickedItem") {
            step("Edit PLN value") {
                screenRobot.editValueOfRow(4, "10")
            }
            step("Change focused item with click") {
                screenRobot.clickOnRow(2)
            }
            step("Check that current value matches edition") {
                screenRobot.assertRowContainsValues(
                    position = 0,
                    values = CurrencyValues(
                        code = "AUD",
                        name = "Australian Dollar",
                        value = "10.00"
                    )
                )
            }
        }
    }

    @Test
    fun invalidValueEnteredResultsInEmptyValues() {
        run("Non numeric value entered to text field results in empty values in list") {
            step("Try edit EUR value") {
                screenRobot.editValueOfRow(0, "Something")
            }
            step("Check that the values of rates are not filled") {
                val values: List<CurrencyValues> = listOf(
                    CurrencyValues(
                        code = "EUR",
                        name = "Euro"
                    ),
                    CurrencyValues(
                        code = "AUD",
                        name = "Australian Dollar"
                    ),
                    CurrencyValues(
                        code = "BRL",
                        name = "Brazil Real"
                    ),
                    CurrencyValues(
                        code = "CAD",
                        name = "Canada Dollar"
                    ),
                    CurrencyValues(
                        code = "PLN",
                        name = "Poland Zloty"
                    ),
                    CurrencyValues(
                        code = "USD",
                        name = "United States Dollar"
                    )
                )
                screenRobot.assertListContainsValues(valuesList = values)
            }
        }
    }
}
