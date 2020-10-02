package pl.olszak.currencies.core

import java.math.BigDecimal
import java.math.RoundingMode

private const val DEFAULT_DECIMAL_PLACES = 2

fun BigDecimal.formatToString(decimalPlaces: Int = DEFAULT_DECIMAL_PLACES): String {
    val new = setScale(decimalPlaces, RoundingMode.HALF_UP)
    return new.toString()
}

fun String.toBigDecimal(default: BigDecimal) =
    toBigDecimalOrNull() ?: default
