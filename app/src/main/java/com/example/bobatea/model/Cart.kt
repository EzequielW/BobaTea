package com.example.bobatea.model

import java.math.BigDecimal

data class Cart(
    val items: List<CartItem>,
    val taxes: BigDecimal,
    val deliveryFee: BigDecimal
)
