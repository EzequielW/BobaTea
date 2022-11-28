package com.example.bobatea.model

data class CartItem(
    val drink: Drink,
    val subtype: String,
    val sweetness: Sweetness,
    val iceQuantity: IceQuantity,
    val topping: Topping?,
    val quantity: Int
)
