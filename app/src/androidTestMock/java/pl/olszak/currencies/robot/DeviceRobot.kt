package pl.olszak.currencies.robot

import pl.olszak.currencies.remote.MockCurrencyApi

class DeviceRobot {

    fun enableInternetConnection() {
        MockCurrencyApi.simulateErrorResponse = false
    }

    fun disableInternetConnection() {
        MockCurrencyApi.simulateErrorResponse = true
    }
}
