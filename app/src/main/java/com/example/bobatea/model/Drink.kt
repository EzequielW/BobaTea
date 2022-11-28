package com.example.bobatea.model

import java.math.BigDecimal

data class Drink(
    val type: String,
    val title: String,
    val description: String,
    val price: BigDecimal,
    val image: Int
)

enum class Sweetness {
    REGULAR, SEMI, EXTRA, NOTHING
}

enum class IceQuantity {
    LOW, MEDIUM, HIGH
}

enum class Topping {
    LARGE_TAPIOCA, SMALL_TAPIOCA, LYCHEE_JELLY
}