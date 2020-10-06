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

    fun clickOnRow(position: Int) {
        CurrenciesScreen {
            itemContainer {
                childAt<CurrenciesScreen.CurrencyRow>(position) {
                    perform { click() }
                }
            }
        }
    }

    fun clickOnRowsEdit(position: Int) {
        CurrenciesScreen {
            itemContainer {
                childAt<CurrenciesScreen.CurrencyRow>(position) {
                    currencyEdit {
                        perform { click() }
                    }
                }
            }
        }
    }

    fun editValueOfRow(position: Int, value: String) {
        CurrenciesScreen {
            itemContainer {
                childAt<CurrenciesScreen.CurrencyRow>(position) {
                    currencyEdit.perform { typeText(value) }
                }
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
                    currencyCodeText.hasText(values.code)
                    currencyNameText.hasText(values.name)
                    currencyEdit.hasText(values.value)
                }
            }
        }
    }

    fun assertRowOrder(codes: List<String>) {
        CurrenciesScreen {
            itemContainer {
                codes.forEachIndexed { position, code ->
                    childAt<CurrenciesScreen.CurrencyRow>(position) {
                        currencyCodeText.hasText(code)
                    }
                }
            }
        }
    }

    fun assertListContainsValues(valuesList: List<CurrencyValues>) {
        CurrenciesScreen {
            itemContainer {
                valuesList.forEachIndexed { position, values ->
                    childAt<CurrenciesScreen.CurrencyRow>(position) {
                        currencyCodeText.hasText(values.code)
                        currencyNameText.hasText(values.name)
                        currencyEdit.hasText(values.value)
                    }
                }
            }
        }
    }
}
