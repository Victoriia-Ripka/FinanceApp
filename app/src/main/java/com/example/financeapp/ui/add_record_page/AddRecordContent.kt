package com.example.financeapp.ui.add_record_page


//import android.graphics.Color
//import com.example.financeapp.ui.theme.CustomCategoryPicker
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financeapp.models.responses.CurrentBalanceCategoriesResponse
import com.example.financeapp.models.responses.CurrentBalanceCategoriesResponse.Category
import com.example.financeapp.services.RetrofitClient.apiService
import com.example.financeapp.ui.theme.CustomChipSelector
import com.example.financeapp.ui.theme.CustomTextField
import com.example.financeapp.ui.theme.CustomTextInknutAntiquaFont
import com.example.financeapp.ui.theme.DatePickerFieldToModal
import com.example.financeapp.viewmodel.UserViewModel
import com.example.pr4_calc.ui.dropdown.DropdownList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun AddRecordContent(
    userViewModel: UserViewModel,
    mainPage: () -> Unit,
    addCategoryPage: () -> Unit
): @Composable () -> Unit {

    val activeTextColor = Color(0xFFFFFFFF)
    val inactiveTextColor = Color(0xFF222831)
    val currency = "UAH"

    val content = @Composable{
        Box(modifier = Modifier
            .padding(top = 80.dp, start = 40.dp, end = 40.dp)
            .verticalScroll(rememberScrollState()))
        {

            val token by userViewModel.token.observeAsState()
            val context = LocalContext.current
            var currentBalanceCategories by remember {
                mutableStateOf(
                    CurrentBalanceCategoriesResponse(
                        currency = "",
                        currentMonth = 0,
                        categories = emptyList()
                    )
                )
            }

            fun showMessageToUser(message: String) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            // Call 2: getCurrentBalanceCategories
            val call2 = apiService.getCurrentBalanceCategories("Bearer $token")
            call2.enqueue(object : Callback<CurrentBalanceCategoriesResponse> {
                override fun onResponse(
                    call: Call<CurrentBalanceCategoriesResponse>,
                    response: Response<CurrentBalanceCategoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            currentBalanceCategories = it
                        }
                    } else {
                        showMessageToUser(response.message())
                    }
                }

                override fun onFailure(call: Call<CurrentBalanceCategoriesResponse>, t: Throwable) {
                    showMessageToUser("Error: ${t.localizedMessage}")
                }
            })

            Column {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        var selected by remember { mutableStateOf(false) }

                        CustomChipSelector(Modifier, "Дохід", "/", "Витрати")

                    }
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        var summa by remember {  mutableStateOf("0") }

                        summa = CustomTextField("", Modifier.width(100.dp), fontSize=30.sp)
                        Text(currency,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 30.sp)
                    }
                }
                Column (
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text("Тип",
                        modifier = Modifier.padding(top = 30.dp, bottom = 20.dp),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary)

                    Row {
                        val radioOptions = listOf("Картка", "Готівка")
                        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
                        radioOptions.forEach { text ->
                            Row(
                                Modifier
                                    .width(140.dp)
                                    .selectable(
                                        selected = (text == selectedOption),
                                        onClick = { onOptionSelected(text) },
                                        role = Role.RadioButton
                                    )
                                    .padding(horizontal = 16.dp),
//                            verticalAlignment = Alignment.Start
                            ){
                                RadioButton(
                                    selected = (text == selectedOption),
                                    onClick = null, // null recommended for accessibility with screen readers
                                    colors = RadioButtonColors(
                                        selectedColor = MaterialTheme.colorScheme.onSecondary,
                                        unselectedColor = MaterialTheme.colorScheme.primaryContainer,
                                        disabledSelectedColor = MaterialTheme.colorScheme.onSecondary,
                                        disabledUnselectedColor = MaterialTheme.colorScheme.primaryContainer
                                    )
                                )
                                Text(
                                    text = text,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(start = 16.dp),
                                    color = if (text == selectedOption) {
                                        MaterialTheme.colorScheme.onSecondary
                                    } else {
                                        MaterialTheme.colorScheme.primaryContainer
                                    }
                                )
                            }
                        }
                    }
                }
                Column (
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text("Назва запису",
                        modifier = Modifier.padding(top = 30.dp, bottom = 10.dp),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary)

                    CustomTextField("", Modifier.padding(0.dp))
                }
                Column (
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text("Категорії",
                        modifier = Modifier.padding(top = 30.dp, bottom = 20.dp),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary)

                    var selectedIndexDrop by rememberSaveable { mutableStateOf(0) }

                    val itemListTemp: List<Category> = listOf(
                        Category(title = "Розваги", total = 0, categoryId = "0"),
                        Category(title = "Іжа", total = 0, categoryId = "1"),
                        Category(title = "Паливо", total = 0, categoryId = "2"),
                        Category(title = "Дім", total = 0, categoryId = "3"),
                        Category(title = "Заробітня плата", total = 0, categoryId = "4"),
                        Category(title = "other", total = 0, categoryId = "5"),
                        Category(title = "+ Додати категорію", total = 0, categoryId = "6"),
                    )

                    val categoryList = arrayListOf<String>()
                    var ind = 0
                    while (ind < itemListTemp.size )
                    {
                        categoryList += itemListTemp[ind].title
                        ind += 1
                    }

                    DropdownList(
                        itemList = categoryList,
                        selectedIndex = selectedIndexDrop,
                        modifier = Modifier,
                        onItemClick = {
                            selectedIndexDrop = it
                            if(categoryList[selectedIndexDrop] == "+ Додати категорію"){
                                Log.d("debug", "add category clicked")
                                addCategoryPage()
                            }
                        })

//                    CustomCategoryPicker()
                }
                Box(modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
                ) {
                    DatePickerFieldToModal()
                }

                Box(modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
                ) {
                    var checked by remember { mutableStateOf(false) }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { checked = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary,
                                uncheckedColor = MaterialTheme.colorScheme.secondary
                            )
                        )
                        Text(
                            "Повторення",
                            color = if(checked){
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.secondary
                            }
                        )
                    }

                    if(checked){
                        val listRepeation = listOf("Щодня", "Щотижня", "Поквартально", "Раз на півроку", "Щорічно")
                        var selectedIndexDrop by remember { mutableStateOf(0) }

                        Column(
                            modifier = Modifier.padding(top = 50.dp)
                        ) {
                            DropdownList(
                                itemList = listRepeation,
                                selectedIndex = selectedIndexDrop,
                                modifier = Modifier,
                                onItemClick = { selectedIndexDrop = it }
                            )
                            CustomTextField("Кількість повторів", Modifier)
                        }

                    } else {
                        Log.d("debug", "Repeating is not checked")
                    }
                }

                Box(modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .border(2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(25)),
                        border = ButtonDefaults.outlinedButtonBorder(false),
                        onClick = mainPage
                    ) {
                        CustomTextInknutAntiquaFont("Додати")
                    }
                }
            }
        }
    }


    return content

}