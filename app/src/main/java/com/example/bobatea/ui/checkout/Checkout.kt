package com.example.bobatea.ui.checkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bobatea.R
import com.example.bobatea.model.*
import com.example.bobatea.util.DottedShape
import java.math.BigDecimal

@Composable
fun Checkout(
    navController: NavController
) {
    val cart = remember {
        Cart(
            mutableStateListOf(
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
        Column {
            TopAppBar(
                title = {
                    Text(
                        "CHECKOUT",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier.size(width = 70.dp, height = 50.dp)
                    ) {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(width = 45.dp, height = 45.dp),
                                imageVector = Icons.Default.Close,
                                tint = Color(0xFFFF0076),
                                contentDescription = null
                            )
                        }
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier.size(width = 70.dp, height = 50.dp)
                    ) {

                    }
                },
                backgroundColor = Color.Transparent,
                contentColor = Color.White
            )
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
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 21.dp)
        ){
            CheckoutItem(
                "Subtotal",
                "$" + bigDecimalToPrice(subtotal),
                Color(0xFF7A85A3)
            )
            CheckoutItem(
                "Taxes",
                "$" + bigDecimalToPrice(cart.taxes),
                Color(0xFF7A85A3)
            )
            CheckoutItem(
                "Delivery Fee",
                "$" + bigDecimalToPrice(cart.deliveryFee),
                Color(0xFF7A85A3)
            )
            CheckoutItem(
                "Total",
                "$" + bigDecimalToPrice(total),
                Color.White
            )
            Row (
                modifier = Modifier.fillMaxWidth().padding(top = 45.dp, bottom = 28.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                OutlinedButton(
                    onClick = {
                          navController.popBackStack()
                    },
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

@Composable
fun CheckoutItem(name: String, value: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            name,
            fontWeight = FontWeight(700),
            color = color,
            textAlign = TextAlign.End
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 5.dp)
        ) {
            Box(
                Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .background(color, shape = DottedShape(step = 10.dp))
            )
        }
        Text(
            value,
            fontWeight = FontWeight(700),
            color = color
        )
    }
}