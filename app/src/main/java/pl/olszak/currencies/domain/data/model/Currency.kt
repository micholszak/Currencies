package pl.olszak.currencies.domain.data.model

import java.math.BigDecimal

data class Currency(
    val code: String,
    val rate: BigDecimal
)
