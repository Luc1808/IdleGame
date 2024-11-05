package com.example.idlegame.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idlegame.viewmodels.ShopItem
import com.example.idlegame.viewmodels.SkillsViewModel


@Composable
fun ShoppingScreen(viewModel: SkillsViewModel) {

    val shopItemList = viewModel.shopItemsUnlocked
    var deleteShopItem by remember { mutableStateOf<ShopItem?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
    ) {
       Text(text = "Shopping Screen", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(shopItemList) { shopItem ->
                ShopItemLook(shopItem = shopItem, onClick = { deleteShopItem = shopItem})
            }
        }

        deleteShopItem?.let { shopItem ->
            AlertDialog(
                title = { Text(text = "Are you sure you want to delete this item?") },
                onDismissRequest = { deleteShopItem = null},
                dismissButton = {
                    Button(onClick = {
                       deleteShopItem = null
                    }) {
                       Text(text = "Cancel")
                    }
                                },
                confirmButton = {
                    Button(onClick = {
                        viewModel.buttonLvlUp(shopItem)
                        deleteShopItem = null
                    }) {
                        Text(text = "Confirm")
                    }
                }
            )
        }
    }
}


@Composable
fun ShopItemLook(shopItem: ShopItem, onClick: () -> Unit) {

    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(BorderStroke(1.dp, color = Color.Black), shape = RoundedCornerShape(8.dp))
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        Image(
            imageVector = shopItem.image,
            contentDescription = shopItem.name,
            modifier = Modifier
                .padding(10.dp)
        )

        Column {
            Text(
                text = shopItem.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = shopItem.description)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingScreenPreview() {
    ShoppingScreen(viewModel = SkillsViewModel())
}

