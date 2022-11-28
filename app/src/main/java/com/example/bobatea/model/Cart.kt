package com.example.bobatea.model

import java.math.BigDecimal
import java.text.DecimalFormat

data class Cart(
    val cartItems: MutableList<CartItem>,
    var taxes: BigDecimal,
    var deliveryFee: BigDecimal
) {
    fun calculateSubtotal(): BigDecimal {
        var subtotal = BigDecimal.valueOf(0)
        for(ci in cartItems) {
            var price = ci.drink.price
            if(ci.topping != null){
                price = price.plus(BigDecimal.valueOf(0.50))
            }

            subtotal = subtotal.plus(price.multiply(ci.quantity.toBigDecimal()))
        }

        return subtotal
    }

    fun calculateTotal(subtotal: BigDecimal): BigDecimal {
        return subtotal.plus(taxes).plus(deliveryFee)
    }
}

fun bigDecimalToPrice(value: BigDecimal): String {
    val pattern = "#0.00";
    val formatter = DecimalFormat(pattern)

    return formatter.format(value)
}