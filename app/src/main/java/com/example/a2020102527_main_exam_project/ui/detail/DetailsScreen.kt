package com.example.a2020102527_main_exam_project.ui.detail
/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a2020102527_main_exam_project.ui.Category
import com.example.a2020102527_main_exam_project.ui.Utils
import com.example.a2020102527_main_exam_project.ui.list.CategoryItem
import com.example.a2020102527_main_exam_project.ui.list.formatDate
import com.example.a2020102527_main_exam_project.ui.theme.Color3
import java.util.Calendar
import java.util.Date

/*
This code defines a Composable function DetailsScreen() along with helper functions
to facilitate the creation of a user interface for entering and editing details,
including items, stores, quantities, dates, and categories.
The UI incorporates various text fields, buttons, icons, and dialogues,
providing a comprehensive user experience.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    id:Int,
    navigateUp:() -> Unit
){
    val viewModel = viewModel<DetailsViewModel>(factory = DetailViewModelFactor(id))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 70.dp,0.dp, 70.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Scaffold {
                DetailEntry(
                    state = viewModel.state,
                    onDateSelected = viewModel::onDateChange,
                    onStoreChange = viewModel::onStoreChange,
                    onItemChange = viewModel::onItemChange,
                    onQtyChange = viewModel::onQtyChange,
                    onCategoryChange = viewModel::onCategoryChange,
                    onDialogDismissed = viewModel::onScreenDialogDismissed,
                    onSaveStore = viewModel::addStore,
                    updateItem = { viewModel.updateShoppingItem(id) },
                    saveItem = viewModel::addShoppingItem
                ) {
                    navigateUp.invoke()
                }
            }
        }
    }
}
@Composable
private fun DetailEntry(
    modifier: Modifier = Modifier,
    state: DetailState,
    onDateSelected:(Date) -> Unit,
    onStoreChange:(String) -> Unit,
    onItemChange:(String) -> Unit,
    onQtyChange:(String) -> Unit,
    onCategoryChange:(Category) -> Unit,
    onDialogDismissed:(Boolean) -> Unit,
    onSaveStore:() -> Unit,
    updateItem:() -> Unit,
    saveItem:() -> Unit,
    navigateUp:() -> Unit
){
    var isNewEnabled by remember{
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.padding(20.dp)
    ){
        OutlinedTextField(
            value = state.item,
            onValueChange = {onItemChange(it)},
            label = {Text(text="Item")},
            modifier = Modifier.fillMaxWidth(),
            colors =  OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color3,
                focusedBorderColor = Color.Black
            ),
            shape = OutlinedTextFieldDefaults.shape
        )
        Spacer(modifier = Modifier.Companion.size((12.dp)))
        Row {
            OutlinedTextField(
                value = state.store,
                onValueChange = {
                    if(isNewEnabled) onStoreChange.invoke(it)
                },
                modifier = Modifier.weight(1f),
                colors =  OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color3,
                    focusedBorderColor = Color.Black
                ),
                shape = OutlinedTextFieldDefaults.shape,
                label = {Text(text = "Store")},
                leadingIcon = {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onDialogDismissed.invoke(!state.isScreenDialogDismissed)
                        })

                }
            )
            if(!state.isScreenDialogDismissed){
                Popup(
                    onDismissRequest = {
                        onDialogDismissed
                            .invoke(!state.isScreenDialogDismissed)
                    }
                 ){
                    Surface(modifier = Modifier.padding(16.dp)){
                        Column {
                            state.storeList.forEach {
                                Text(text = it.storeName,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable {
                                            onStoreChange.invoke(it.storeName)
                                            onDialogDismissed(!state.isScreenDialogDismissed)
                                        }
                                )
                            }
                        }
                    }

                }
            }

            TextButton(onClick = {
                isNewEnabled = if(isNewEnabled){
                    onSaveStore.invoke()
                    !isNewEnabled
                }else{
                    !isNewEnabled
                }
            }) {
                Text(text = if(isNewEnabled) "Save" else "New", color = Color3)

            }
        }
        Spacer(modifier = Modifier.size((12.dp)))
        Row(horizontalArrangement = Arrangement.SpaceEvenly){
            OutlinedTextField(value = state.qty,
                onValueChange = {onQtyChange(it)},
                label = { Text(text = "Qty")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(150.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color3,
                    focusedBorderColor = Color.Black
                ),
                shape = OutlinedTextFieldDefaults.shape
            )
            Spacer(modifier = Modifier.size((16.dp)))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color3
                )
                Spacer(modifier = Modifier.size((4.dp)))
                Text(text = formatDate(state.date), color = Color3)
                Spacer(modifier = Modifier.size(4.dp))
                val mDatePicker = datePickerDialog(
                    context = LocalContext.current,
                    onDateSelected = {date ->
                        onDateSelected.invoke(date)

                    }
                )
                IconButton(onClick = { mDatePicker.show()}) {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color3
                    )
                }

            }

        }
        Spacer(modifier = Modifier.size((12.dp)))
        LazyRow {
            items(Utils.category){ category:Category ->
                CategoryItem(
                    iconRes = category.resId,
                    title = category.title,
                    selected = category == state.category
                ){
                    onCategoryChange(category)
                }
                Spacer(modifier = Modifier.size((16.dp)))
            }
        }
        val buttonTitle = if(state.isUpdatingItem) "Update Item"
                                else "Add Item"
        Button(
            onClick = {
                when(state.isUpdatingItem){
                    true -> {
                        updateItem.invoke()
                    }
                    false -> {
                        saveItem.invoke()
                    }
                }
                navigateUp.invoke()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.item.isNotEmpty() &&
                        state.store.isNotEmpty() &&
                        state.qty.isNotEmpty(),
            shape = OutlinedTextFieldDefaults.shape
        ){
            Text(text = buttonTitle, color = Color3)
        }
    }
}

@Composable
fun datePickerDialog(
    context: Context,
    onDateSelected: (Date) -> Unit
): DatePickerDialog{
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()


    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYears:Int, mMonth:Int, mDayofMonth:Int ->
            val calender = Calendar.getInstance()
            calender.set(mYears,mMonth,mDayofMonth)
            onDateSelected.invoke(calender.time)
        },year,month,day
    )
    return mDatePickerDialog
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailEntry(){
    DetailEntry(
        state = DetailState(),
        onDateSelected = {},
        onStoreChange = {},
        onItemChange = {},
        onQtyChange = {},
        onCategoryChange = {},
        onDialogDismissed = {},
        onSaveStore = { /*TODO*/ },
        updateItem = { /*TODO*/ },
        saveItem = { /*TODO*/ }) {
        
    }
}

