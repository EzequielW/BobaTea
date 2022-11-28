package com.example.bobatea.ui.checkout

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bobatea.R
import com.example.bobatea.model.*
import java.math.BigDecimal

@Composable
fun Checkout() {
    val cart = remember {
        Cart(
            mutableStateListOf<CartItem>(
                CartItem(
                    Drink("regular", "Milk tea", "Sweet tint of caramel and chocolate",
                        BigDecimal.valueOf(4.99), R.drawable.milk_tea),
                    "milk",
                    Sweetness.EXTRA,
                    IceQuantity.MEDIUM,
                    Topping.LARGE_TAPIOCA,
                    2
                )
            ),
            BigDecimal.valueOf(1.00),
            BigDecimal.valueOf(3.00)
        )
    }
    var subtotal by remember { mutableStateOf(cart.calculateSubtotal()) }
    var total by remember { mutableStateOf(cart.calculateTotal(subtotal)) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = Color.Black),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ){
            itemsIndexed(cart.cartItems) { index, ci ->
                Column(
                    modifier = Modifier
                        .padding(horizontal = 21.dp)
                        .padding(bottom = 24.dp)
                ) {
                    CartItemCard(
                        ci,
                        {
                            if(ci.quantity > 1){
                                cart.cartItems[index] = ci.copy(quantity = ci.quantity - 1)
                            }
                            subtotal = cart.calculateSubtotal()
                            total = cart.calculateTotal(subtotal)
                        },
                        {
                            cart.cartItems[index] = ci.copy(quantity = ci.quantity + 1)
                            subtotal = cart.calculateSubtotal()
                            total = cart.calculateTotal(subtotal)
                        }
                    )
                }
                Divider(
                    color = Color(0xFF7A85A3).copy(alpha = 0.4f),
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 21.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    "Subtotal",
                    fontWeight = FontWeight(700),
                    color = Color(0xFF7A85A3)
                )
                Column(Modifier.fillMaxWidth()){
                    Canvas(Modifier.height(1.dp).padding(horizontal = 10.dp)) {
                        drawLine(
                            color = Color.White,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 30f), 0f),
                            strokeWidth = 5f
                        )
                    }
                }
                Text(
                    "$" + bigDecimalToPrice(subtotal),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF7A85A3)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Taxes",
                    fontWeight = FontWeight(700),
                    color = Color(0xFF7A85A3)
                )
                Text(
                    "$" + bigDecimalToPrice(cart.taxes),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF7A85A3)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Delivery Fee",
                    fontWeight = FontWeight(700),
                    color = Color(0xFF7A85A3)
                )
                Text(
                    "$" + bigDecimalToPrice(cart.deliveryFee),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF7A85A3)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 90.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Order Total",
                    fontWeight = FontWeight(700),
                    color = Color.White
                )
                Text(
                    "$" + bigDecimalToPrice(total),
                    fontWeight = FontWeight(700),
                    color = Color.White
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    border = BorderStroke(1.dp, Color(0xFFFF0076)),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(),
                    modifier = Modifier.width(145.dp)
                ) {
                    Text(
                        "CANCEL",
                        color = Color.White,
                        fontSize = 19.sp
                    )
                }
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0076)),
                    contentPadding = PaddingValues(),
                    modifier = Modifier.width(145.dp)
                ) {
                    Text(
                        "NEXT",
                        color = Color.White,
                        fontSize = 19.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemCard(cartItem: CartItem, onRemove: () -> Unit, onAdd: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Text(
                cartItem.drink.type.uppercase() + " " + cartItem.drink.title.uppercase() ,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp),
                fontSize = 14.sp
            )
            Text(
                cartItem.subtype.uppercase(),
                color = Color(0xFF7A85A3),
                fontSize = 19.sp
            )
            Text(
                cartItem.sweetness.sweet.uppercase(),
                color = Color(0xFF7A85A3),
                fontSize = 19.sp
            )
            Text(
                cartItem.iceQuantity.name + " ICE",
                color = Color(0xFF7A85A3),
                fontSize = 19.sp
            )
            if(cartItem.topping != null) {
                Text(
                    cartItem.topping.topping.uppercase(),
                    color = Color(0xFF7A85A3),
                    fontSize = 19.sp
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight(),
        ) {
            Text("QTY", color = Color.White, modifier = Modifier.padding(bottom = 6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 6.dp)
            ) {
                IconButton(
                    onClick = onRemove,
                    enabled = cartItem.quantity > 1
                ) {
                    Icon(
                        Icons.Default.Remove,
                        contentDescription = null,
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp),
                        tint =
                            if (cartItem.quantity > 1) Color(0xFFFF0076)
                            else Color(0xFF7A85A3).copy(alpha = 0.3f)
                    )
                }
                Text(
                    cartItem.quantity.toString(),
                    color = Color.White,
                    modifier = Modifier
                        .background(color = Color(0xFF7A85A3).copy(alpha = 0.3f))
                        .padding(vertical = 15.dp, horizontal = 20.dp)
                )
                IconButton(onClick = onAdd) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp),
                        tint = Color(0xFFFF0076)
                    )
                }
            }
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
    ) {
        Text(
            "EDIT",
            color = Color(0xFFFF0076),
            modifier = Modifier
                .clickable { }
                .padding(end = 20.dp),
            fontSize = 14.sp
        )
        Divider(
            color = Color(0xFF7A85A3),
            modifier = Modifier
                .height(20.dp)  //fill the max height
                .width(1.dp)
        )
        Text(
            "REMOVE",
            color = Color(0xFFFF0076),
            modifier = Modifier
                .clickable { }
                .padding(start = 20.dp),
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutPreview() {
    Checkout()
}