package com.skillbox.compose.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skillbox.compose.R

@Composable
fun Appbar(navController: NavController, title: String) {
    TopAppBar(
        backgroundColor = Color.Black,
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back icon",
                Modifier.padding(start = 4.dp),
                Color.White
            )
            IconButton(onClick = { navController.popBackStack() }) {
            }
        },
        title = { Text(text = title, color = Color.White) }
    )
}