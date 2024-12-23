package com.example.financeapp.ui.theme

import android.util.MutableBoolean
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomChipSelector(
    modifier: Modifier,
    option1Text: String,
    separator: String,
    option2Text: String
) {
    var selected1 by remember { mutableStateOf(false) }
    var selected2 by remember { mutableStateOf(true) }
    val activeTextColor = MaterialTheme.colorScheme.onPrimary
    val inactiveTextColor = MaterialTheme.colorScheme.onBackground

    val chipColors = FilterChipDefaults.filterChipColors(
        containerColor = MaterialTheme.colorScheme.background,
        selectedContainerColor = MaterialTheme.colorScheme.background,
        disabledSelectedContainerColor = MaterialTheme.colorScheme.background

    )

    FilterChip(
        onClick = {
            selected1 = !selected1
            selected2 = !selected2
          },
        label = {
            Text(option1Text, color = if(selected1) {activeTextColor} else {inactiveTextColor}, fontSize = 30.sp)
        },
        selected = selected1,
        colors = chipColors,
        border = FilterChipDefaults.filterChipBorder(false, false)
    )

    Text(text = separator, color = Color(0xFF00ADB5), fontSize = 30.sp)

    FilterChip(
        selected = !selected2,
        onClick = {
            selected1 = !selected1
            selected2 = !selected2
        },
        label = {
            Text(option2Text, color = if(selected2) {activeTextColor} else {inactiveTextColor}, fontSize = 30.sp)
        },
        colors = chipColors,
        border = FilterChipDefaults.filterChipBorder(false, false)
    )
}

@Composable
fun CustomTextField(
    label: String,
    modifier: Modifier
) :String{
    var value by remember { mutableStateOf("") }

    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { value = it },
        label = { Text(label) },
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary
        )
    )

    return value
}


