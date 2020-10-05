package pl.olszak.currencies.robot

import pl.olszak.currencies.robot.model.CurrencyValues
import pl.olszak.currencies.screen.CurrenciesScreen

class CurrenciesScreenRobot {

    fun clickOnErrorPrompt() {
        CurrenciesScreen {
            connectionErrorText {
                perform { click() }
            }
        }
    }

    fun assertErrorStateIsVisible() {
        CurrenciesScreen {
            errorContainer {
                isVisible()
            }
        }
    }

    fun assertErrorStateIsGone() {
        CurrenciesScreen {
            errorContainer {
                isGone()
            }
        }
    }

    fun assertItemContainerIsVisible() {
        CurrenciesScreen {
            itemContainer {
                isVisible()
            }
        }
    }

    fun assertThatListContainsItems(size: Int) {
        CurrenciesScreen {
            itemContainer {
                hasSize(size)
            }
        }
    }

    fun assertEveryRowsItemVisible() {
        CurrenciesScreen {
            itemContainer {
                children<CurrenciesScreen.CurrencyRow> {
                    val viewList =
                        listOf(flagImage, currencyCodeText, currencyEdit, currencyNameText)
                    viewList.forEach { view ->
                        view.isVisible()
                    }
                }
            }
        }
    }

    fun assertRowContainsValues(position: Int, values: CurrencyValues) {
        CurrenciesScreen {
            itemContainer {
                childAt<CurrenciesScreen.CurrencyRow>(position) {
                    currencyCodeText.containsText(values.code)
                    currencyNameText.containsText(values.name)
                    currencyEdit.containsText(values.value)
                }
            }
        }
    }

    fun performClickOnRow(position: Int) {
        CurrenciesScreen {
            itemContainer {
                childAt<CurrenciesScreen.CurrencyRow>(position) {
                    perform { click() }
                }
            }
        }
    }

    fun assertRowOrder(codes: List<String>) {
        CurrenciesScreen {
            itemContainer {
                codes.forEachIndexed { position, code ->
                    childAt<CurrenciesScreen.CurrencyRow>(position) {
                        currencyCodeText.containsText(code)
                    }
                }
            }
        }
    }
}
