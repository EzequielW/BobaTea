package com.example.bobatea.ui.product_detail

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
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
import com.example.bobatea.model.*
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
    var type by remember { mutableStateOf(DrinkOption.SWEETNESS) }

    var subtypeOption by remember { mutableStateOf("MILK") }
    var sweetnessOption by remember { mutableStateOf(Sweetness.REGULAR.naming) }
    var iceQuantityOption by remember { mutableStateOf(IceQuantity.MEDIUM.naming) }
    var toppingOption by remember { mutableStateOf(Topping.NONE) }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            SelectContent(
                title,
                selectOptions,
                selectedOption,
                type
            ) { option, type ->
                when (type) {
                    DrinkOption.SUBTYPE -> subtypeOption = option
                    DrinkOption.SWEETNESS -> sweetnessOption = option
                    DrinkOption.ICE_QUANTITY -> iceQuantityOption = option
                }
                selectedOption = option
            }
        },
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
                    subtypeOption,
                    sweetnessOption,
                    iceQuantityOption,
                    toppingOption,
                    onClickSelect = { name, selectList, selected, t ->
                        title = name
                        selectOptions = selectList
                        type = t
                        selectedOption = selected

                        coroutineScope.launch {
                            sheetState.show()
                        }
                    },
                    onTopping = { topping ->
                        toppingOption = topping
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
fun ProductOptions(
    subtypeOption: String,
    sweetnessOption: String,
    iceQuantityOption: String,
    toppingOption: Topping,
    onClickSelect: (String, List<String>, String, DrinkOption) -> Unit,
    onTopping: (Topping) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 26.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
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
                        subtypeOption,
                        DrinkOption.SUBTYPE
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
                        subtypeOption,
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
                        sweetnessOption,
                        DrinkOption.SWEETNESS
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
                        sweetnessOption.uppercase(),
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
                        iceQuantityOption,
                        DrinkOption.ICE_QUANTITY
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
                        "W/ " + iceQuantityOption.uppercase() + " ICE",
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
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White,
                        backgroundColor =
                            if(toppingOption == Topping.LARGE_TAPIOCA)
                                Color(0xFFFF0076)
                            else Color.Transparent
                    ),
                    onClick = {
                        if(toppingOption == Topping.LARGE_TAPIOCA){
                            onTopping(Topping.NONE)
                        } else{
                            onTopping(Topping.LARGE_TAPIOCA)
                        }
                    }
                ) {
                    Text(
                        Topping.LARGE_TAPIOCA.naming.uppercase(),
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
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White,
                        backgroundColor =
                            if(toppingOption == Topping.SMALL_TAPIOCA)
                                Color(0xFFFF0076)
                            else Color.Transparent
                    ),
                    onClick = {
                        if(toppingOption == Topping.SMALL_TAPIOCA){
                            onTopping(Topping.NONE)
                        } else{
                            onTopping(Topping.SMALL_TAPIOCA)
                        }
                    }
                ) {
                    Text(
                        Topping.SMALL_TAPIOCA.naming.uppercase(),
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
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White,
                        backgroundColor =
                            if(toppingOption == Topping.LYCHEE_JELLY)
                                Color(0xFFFF0076)
                            else Color.Transparent
                    ),
                    onClick = {
                        if(toppingOption == Topping.LYCHEE_JELLY){
                            onTopping(Topping.NONE)
                        } else{
                            onTopping(Topping.LYCHEE_JELLY)
                        }
                    }
                ) {
                    Text(
                        Topping.LYCHEE_JELLY.naming.uppercase(),
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
                if(toppingOption == Topping.NONE) "$4.99" else "$5.49",
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
    type: DrinkOption,
    onSelect: (String, DrinkOption) -> Unit
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
                    onSelect(option, type)
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