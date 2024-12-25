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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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

@Composable
fun CustomPasswordInput(
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
        textStyle = TextStyle(fontSize = fontSize),
        visualTransformation = PasswordVisualTransformation('\u002A'),
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
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

// ----------------------------------

@Composable
fun ChangeValueDialog(
    onDismissRequest: () -> Unit,
    label: String,
    placeholder: String
) :String{
    var new_value by remember { mutableStateOf("") }
    var isDone by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
//                .fillMaxWidth()
                .width(460.dp)
                .padding(10.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Column (
                modifier = Modifier
                    .padding(30.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        label,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 10.dp),
                        textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(10.dp))
                    new_value = CustomTextField(placeholder, Modifier, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = {
                            new_value = ""
                            onDismissRequest()
                        },
//                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp),
                    ) {
                        Text("Вийти", fontSize = 14.sp)
                    }
                    TextButton(
                        onClick = { onDismissRequest() },
//                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp),
                    ) {
                        Text("Підтвердити", fontSize = 14.sp)
                    }
                }
            }
        }
    }
    return new_value
}


// ----------------------------------

// maybe will use in the future
// !!! Only patterns, they are not done yet !!!


@Composable
fun EmailInputField() {
    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Regular expression to validate email format
    val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"

    Column(modifier = Modifier) {
        TextField(
            value = email,
            onValueChange = { input ->
                email = input
                errorMessage = if (input.matches(emailPattern.toRegex())) {
                    null
                } else {
                    "Please enter a valid email address."
                }
            },
            label = { Text("Enter Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            isError = errorMessage != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun PasswordTextFieldWithToggle() {
    val (password, setPassword) = remember { mutableStateOf("") }
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = setPassword,
        label = { Text("Password") },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Lock else Icons.Filled.CheckCircle
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = { setPasswordVisible(!passwordVisible) }) {
                Icon(image, contentDescription = description)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
    )


    /*
    The expression (password, setPassword) in the lines:

    val (password, setPassword) = remember { mutableStateOf("") }
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(false) }
    is known as destructuring declaration in Kotlin, which allows you to unpack the values returned by certain functions into separate variables directly. Let’s break down this concept and how it applies in this context:

    Understanding Destructuring Declaration
    mutableStateOf(""):

    mutableStateOf("") is a function that creates a state object initialized with an empty string (""). It returns a MutableState object, which contains the current value and a function to update that value.
    remember { mutableStateOf("") }:

    The remember function ensures that the state is retained across recompositions of the composable. In other words, it keeps the state value consistent as the composable function is called multiple times.
    Destructuring MutableState:

    The MutableState object returned by mutableStateOf has a value property that holds the current state and a setter function that updates this value.
    Instead of accessing these properties manually (state.value and state.value = newValue), Kotlin allows you to destructure this object into two variables: one for reading the value and one for updating it.
    Destructuring in Action
    val (password, setPassword) = remember { mutableStateOf("") }:

    password: This variable holds the current value of the state ("" initially).
    setPassword: This function is used to update the state value.
    This line essentially means that password refers to the current state value, and setPassword is used to change it.
    Equivalent Code without Destructuring:

    If you didn't use destructuring, you would typically handle the state like this:

    val passwordState = remember { mutableStateOf("") }

    val password = passwordState.value        // To access the current value
    passwordState.value = "newPassword"       // To update the value
    Using destructuring makes this much cleaner and more readable, allowing you to directly interact with the state.

    Benefits of Destructuring in This Context
    Readability: It makes the code cleaner and easier to understand by separating the current value (password) and its updater function (setPassword).

    Simplified State Management: It abstracts away the MutableState management and provides a straightforward way to work with state values and setters.

    Conciseness: The destructuring approach reduces the verbosity of managing state, especially in composable functions where state is frequently read and updated.
         */
}