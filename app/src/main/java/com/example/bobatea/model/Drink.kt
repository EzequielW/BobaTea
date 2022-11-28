package com.example.bobatea.model

import java.math.BigDecimal

data class Drink(
    val type: String,
    val title: String,
    val description: String,
    val price: BigDecimal,
    val image: Int
)

enum class Sweetness(val sweet: String) {
    REGULAR("regular sweet"),
    SEMI("semi sweet"),
    EXTRA("extra sweet"),
    NOTHING("no sweet")
}

enum class IceQuantity {
    LOW, MEDIUM, HIGH
}

enum class Topping(val topping: String) {
    LARGE_TAPIOCA("large tapioca"),
    SMALL_TAPIOCA("small tapioca"),
    LYCHEE_JELLY("lychee jelly")
}