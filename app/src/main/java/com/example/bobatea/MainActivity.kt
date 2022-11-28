package com.example.bobatea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bobatea.ui.product_detail.ProductDetail
import com.example.bobatea.ui.product_list.ProductList
import com.example.bobatea.ui.theme.BobaTeaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BobaTeaApp()
        }
    }
}

@Composable
fun BobaTeaApp() {
    BobaTeaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ProductDetail()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BobaTeaApp()
}