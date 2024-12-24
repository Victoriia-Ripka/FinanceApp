package com.example.financeapp.ui.theme

import android.util.Log
import android.util.MutableBoolean
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.financeapp.models.responses.CurrentBalanceCategoriesResponse.Category
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun CustomChipSelector(
    modifier: Modifier,
    option1Text: String,
    separator: String,
    option2Text: String
) {
    var selected1 by remember { mutableStateOf(false) }
    var selected2 by remember { mutableStateOf(true) }
    val activeTextColor = MaterialTheme.colorScheme.onSecondary
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
        border = FilterChipDefaults.filterChipBorder(
            true,
            true)
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
        border = FilterChipDefaults.filterChipBorder(
            true,
            true)
    )
}

// ----------------------------------

@Composable
fun CustomTextField(
    label: String,
    modifier: Modifier,
    fontSize: TextUnit = 16.sp
) :String{
    var value by remember { mutableStateOf("") }

    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { value = it },
        label = { Text(label, fontSize = fontSize)},
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
        ),
        textStyle = TextStyle(fontSize = fontSize)
    )

    return value
}

// ----------------------------------

val itemListTemp: List<Category> = listOf(
    Category(title = "Розваги", total = 0, categoryId = "0"),
    Category(title = "Іжа", total = 0, categoryId = "1"),
    Category(title = "Паливо", total = 0, categoryId = "2"),
    Category(title = "Дім", total = 0, categoryId = "3"),
    Category(title = "Заробітня плата", total = 0, categoryId = "4"),
    Category(title = "other", total = 0, categoryId = "5"),
    Category(title = "+ Додати категорію", total = 0, categoryId = "6"),
    )

@Composable
fun CustomCategoryPicker (
    itemList: List<Category> = itemListTemp
) {
    LazyColumn {

        var ind = 0
        Log.d("debug", "Assert: ${(itemList.size % 2)}")
        val size = if(itemList.size % 2 != 0) { (itemList.size / 2) + 1 } else { itemList.size / 2 }

        items(size) { index ->
            val item1 = itemList[ind].title
            val item2 = if ((itemList.size) <= ind+1) {
                ""
            } else {
                itemList[ind + 1].title
            }
            var selected = false
            val chipColors = FilterChipDefaults.filterChipColors(
                containerColor = MaterialTheme.colorScheme.background,
                labelColor = MaterialTheme.colorScheme.onSecondary,
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.background,
                disabledSelectedContainerColor = MaterialTheme.colorScheme.primary

            )
            Row (
                modifier = Modifier.padding(vertical = 2.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilterChip(
                    modifier = Modifier.weight(0.5f).padding(horizontal = 5.dp),
                    selected = selected,
                    onClick = {
                        selected = !selected
                    },
                    label = {
                        Text(item1)
                    },
                    colors = chipColors,
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = selected,
                        borderColor = MaterialTheme.colorScheme.primary)
                )
                if (item2 != "") {
                    FilterChip(
                        modifier = Modifier.weight(0.5f).padding(horizontal = 5.dp),
                        selected = selected,
                        onClick = {
                            selected = !selected
                        },
                        label = {
                            Text(item2)
                        },
                        colors = chipColors,
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = selected,
                            borderColor = MaterialTheme.colorScheme.primary)
                    )
                }
            }
            ind += 2
        }
    }
}

// ----------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK", color = MaterialTheme.colorScheme.secondary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MaterialTheme.colorScheme.secondary)
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun DatePickerFieldToModal(modifier: Modifier = Modifier) {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedDate?.let { convertMillisToDate(it) } ?: "",
        onValueChange = { },
        label = { Text("Date") },
        placeholder = { Text("DD/MM/YYYY", color = MaterialTheme.colorScheme.secondary) },
        trailingIcon = {
            Icon(
                Icons.Default.DateRange,
                contentDescription = "Select date",
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary

        ),
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                    // in the Initial pass to observe events before the text field consumes them
                    // in the Main pass.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
    )

    if (showModal) {
        DatePickerModal(
            onDateSelected = { selectedDate = it },
            onDismiss = { showModal = false }
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
