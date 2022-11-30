package com.example.bobatea.ui.product_detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
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
import androidx.navigation.NavController
import com.example.bobatea.model.Cart
import com.example.bobatea.model.Drink
import com.example.bobatea.model.IceQuantity
import com.example.bobatea.model.Sweetness
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductDetail(navController: NavController, drink: Drink, cart: Cart) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    var title by remember { mutableStateOf("SELECT SWEETNESS LEVEL") }
    var selectOptions by remember { mutableStateOf(listOf("")) }
    var selectedOption by remember { mutableStateOf(Sweetness.SEMI.naming) }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            SelectContent(
                title,
                selectOptions,
                selectedOption,
                onSelect = { option ->
                    selectedOption = option
                }
            )},
        modifier = Modifier.fillMaxWidth(),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .verticalScroll(rememberScrollState()),
            ) {
                ProductImageCard(navController, drink, cart)
                ProductOptions(
                    onClickSelect = { name, selectList, selected ->
                        title = name
                        selectOptions = selectList
                        selectedOption = selected
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    }
                )
            }
        }
    )
}

@Composable
fun ProductImageCard(navController: NavController, drink: Drink, cart: Cart) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 268.dp)
    ) {
        Box {
            Image(
                painterResource(drink.image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize()
            )
            Row (
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Default.Close,
                            "backIcon",
                            modifier = Modifier
                                .width(45.dp)
                                .height(45.dp),
                            tint = Color(0xFFFF0076)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(28.dp)
                        .widthIn(0.dp, 155.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    val textModifier = Modifier.align(Alignment.End)

                    Text(
                        "REGULAR",
                        modifier = textModifier,
                        fontSize = 22.sp,
                        fontWeight = FontWeight(400),
                        letterSpacing = 0.08.em,
                        textAlign = TextAlign.End
                    )
                    Text(
                        "MILK TEA",
                        modifier = textModifier,
                        fontSize = 42.sp,
                        fontWeight = FontWeight(700),
                        letterSpacing = 0.08.em,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Composable
fun ProductOptions(onClickSelect: (String, List<String>, String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 26.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        var subtypeOption = "MILK"
        var sweetnessOption = Sweetness.REGULAR.naming
        var iceQuantityOption = IceQuantity.MEDIUM.naming
        val subtypeSelect = listOf(
            "MILK",
            "ALMOND MILK"
        )
        val sweetnessSelect = listOf(
            Sweetness.REGULAR.naming,
            Sweetness.SEMI.naming,
            Sweetness.EXTRA.naming,
            Sweetness.NOTHING.naming
        )
        val iceQuantitySelect = listOf(
            IceQuantity.HIGH.naming,
            IceQuantity.MEDIUM.naming,
            IceQuantity.LOW.naming
        )

        Column {
            OutlinedButton(
                contentPadding = PaddingValues(),
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(2.dp, Color.Gray),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
                shape = RoundedCornerShape(1.dp),
                onClick = {
                    onClickSelect(
                        "SELECT MILK TYPE",
                        subtypeSelect,
                        subtypeOption
                    )
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "MILK",
                        fontSize = 22.sp,
                        fontWeight = FontWeight(400),
                        color = Color.White,
                        letterSpacing = 0.08.em,
                    )
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "",
                        tint = Color(0xFFFF0076)
                    )
                }
            }
            OutlinedButton(
                contentPadding = PaddingValues(),
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(2.dp, Color.Gray),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
                shape = RoundedCornerShape(1.dp),
                onClick = {
                    onClickSelect(
                        "SELECT SWEETNESS LEVEL",
                        sweetnessSelect,
                        sweetnessOption
                    )
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "REGULAR MILK",
                        fontSize = 22.sp,
                        fontWeight = FontWeight(400),
                        color = Color.White,
                        letterSpacing = 0.08.em,
                    )
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "",
                        tint = Color(0xFFFF0076)
                    )
                }
            }
            OutlinedButton(
                contentPadding = PaddingValues(),
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(2.dp, Color.Gray),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
                shape = RoundedCornerShape(1.dp),
                onClick = {
                    onClickSelect(
                        "SELECT ICE QUANTITY",
                        iceQuantitySelect,
                        iceQuantityOption
                    )
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "W/ MEDIUM ICE",
                        fontSize = 22.sp,
                        fontWeight = FontWeight(400),
                        color = Color.White,
                        letterSpacing = 0.08.em,
                    )
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "",
                        tint = Color(0xFFFF0076)
                    )
                }
            }
            Row (
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 18.dp)
            ){
                Text(
                    "Tap to select toppings ",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    letterSpacing = 0.08.em,
                )
                Text(
                    "$0.50 per topping",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    letterSpacing = 0.08.em,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    contentPadding = PaddingValues(),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, Color(0xFFFF0076)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = Color.Transparent),
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        "LARGE TAPIOCA",
                        modifier = Modifier
                            .padding(vertical = 28.dp, horizontal = 4.dp)
                            .widthIn(max = 87.dp),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
                OutlinedButton(
                    contentPadding = PaddingValues(),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, Color(0xFFFF0076)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = Color.Transparent),
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        "SMALL TAPIOCA",
                        modifier = Modifier
                            .padding(vertical = 28.dp, horizontal = 4.dp)
                            .widthIn(max = 87.dp),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
                OutlinedButton(
                    contentPadding = PaddingValues(),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, Color(0xFFFF0076)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = Color.Transparent),
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        "LYCHEE JELLY",
                        modifier = Modifier
                            .padding(vertical = 28.dp, horizontal = 4.dp)
                            .widthIn(max = 87.dp),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Column(
            modifier = Modifier.padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                "TOTAL:",
                fontSize = 19.sp,
                fontWeight = FontWeight(700),
                color = Color.Gray
            )
            Text(
                "$4.99",
                fontSize = 19.sp,
                fontWeight = FontWeight(700),
                color = Color.White
            )
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0076)),
                shape = RoundedCornerShape(50),
                onClick = { /*TODO*/ }
            ) {
                Text(
                    "ADD",
                    modifier = Modifier.padding(horizontal = 32.dp),
                    fontSize = 19.sp,
                    fontWeight = FontWeight(700),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun SelectContent(
    title: String,
    options: List<String>,
    selectedOption: String,
    onSelect: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(title)
        }
        options.forEach { option ->
            Divider(
                modifier = Modifier.fillMaxWidth().height(1.dp),
                color = Color(0xFF7A85A3).copy(alpha = 0.5f)
            )
            Column(
                modifier = Modifier.clickable {
                    onSelect(option)
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    horizontalArrangement = if(option == selectedOption) Arrangement.SpaceEvenly else Arrangement.Center
                ) {
                    if(option == selectedOption) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                    Text(
                        option.uppercase(),
                        fontSize = 19.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFFFF0076)
                    )
                    if(option == selectedOption) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.Transparent)
                    }
                }
            }
        }
    }
}