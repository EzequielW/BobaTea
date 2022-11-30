package com.example.bobatea.ui.product_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bobatea.AppRoute
import com.example.bobatea.model.Drink

@Composable
fun ProductList(navController: NavHostController, drinkList: MutableList<Drink>){
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        items(drinkList) { drink ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 360.dp)) {
                ProductCard(
                    drink = drink,
                    onAdd = {
                        navController.navigate(
                            AppRoute.ProductDetail.route
                                .replace("{drink}", drinkList.indexOf(drink).toString())
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun ProductCard(drink: Drink, onAdd: () -> Unit){
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(1.dp)
    ) {
        Box {
            Image(
                painterResource(drink.image),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.End
                ) {
                Column(
                    modifier = Modifier
                        .padding(18.dp)
                        .widthIn(0.dp, 200.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    val textModifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = 16.dp)

                    Text(
                        drink.type.uppercase(),
                        modifier = textModifier,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(900)
                    )
                    Text(
                        drink.description.uppercase(),
                        modifier = textModifier,
                        textAlign = TextAlign.End,
                        fontSize = 22.sp,
                        fontWeight = FontWeight(400),
                        lineHeight = 26.sp,
                        letterSpacing = 0.08.em
                    )
                    Text(
                        "$" + drink.price.toString(),
                        modifier = textModifier,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(900)
                    )
                    Text(
                        "ADD",
                        modifier = textModifier
                            .clickable{ onAdd() },
                        fontSize = 16.sp,
                        fontWeight = FontWeight(900),
                        color = Color(0xFFFF0076)
                    )
                }
            }
        }
    }
}