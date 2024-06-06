package com.example.a2020102527_main_exam_project.ui.list
/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/
import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a2020102527_main_exam_project.data.room.ItemsWithStoreAndList
import com.example.a2020102527_main_exam_project.data.room.models.Item
import com.example.a2020102527_main_exam_project.ui.Category
import com.example.a2020102527_main_exam_project.ui.Utils
import com.example.a2020102527_main_exam_project.ui.theme.Color3
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/*
This code defines a composable function ListScreen() using
Jetpack Compose to create the UI for displaying a list of items.
It includes category selection, item cards with swipe-to-dismiss functionality,
and a floating action button for adding new items.
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(
    onNavigate:(Int) -> Unit
){
    val listViewModel = viewModel(modelClass = ListViewModel::class.java)
    val listState = listViewModel.state
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 70.dp, bottom = 70.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(onClick = { onNavigate.invoke(-1) }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add list",
                            tint = Color3
                        )
                    }
                }
            ) {
                LazyColumn {
                    item {
                        LazyRow {
                            items(Utils.category) { category: Category ->
                                CategoryItem(
                                    iconRes = category.resId,
                                    title = category.title,
                                    selected = category == listState.category
                                ) {
                                    listViewModel.onCategoryChange(category)
                                }
                                Spacer(modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                    items(listState.items) {
                        val dismissState = rememberDismissState(
                            confirmValueChange = { value ->
                                if (value == DismissValue.DismissedToEnd) {
                                    listViewModel.deleteItem(it.item)
                                }
                                true
                            }
                        )
                        SwipeToDismiss(
                            state = dismissState,
                            background = {
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Red
                                ) {
                                    // Optional: Add content for the background of the swipe
                                }
                            },
                            dismissContent = {
                                ShoppingItems(
                                    item = it,
                                    isChecked = it.item.isChecked,
                                    onCheckedChange = listViewModel::onItemCheckedChange
                                ) {
                                    onNavigate.invoke(it.item.id)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun CategoryItem(
    @DrawableRes iconRes: Int,
    title:String,
    selected:Boolean,
    onItemClick:() -> Unit
){
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
            .selectable(
                selected = selected,
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(),
                onClick = { onItemClick.invoke() }
            ),
        border = BorderStroke(
            1.dp,
            if(selected) MaterialTheme.colorScheme.primary.copy(.5f)
            else MaterialTheme.colorScheme.primary
        ),
        shape = CardDefaults.shape,
        colors = CardDefaults
            .cardColors(

                containerColor = if(selected) Color3.copy(.5F)
                else MaterialTheme.colorScheme.surface,
                contentColor = if(selected) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.onSurface
            )
        ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
            ){
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )
        }

    }
}

@Composable
fun ShoppingItems(
    item: ItemsWithStoreAndList,
    isChecked:Boolean,
    onCheckedChange:(Item, Boolean) -> Unit,
    onItemClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke()
            }
            .padding(8.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ){
            Column(modifier = Modifier.padding(8.dp)){
                Text(
                    text = item.item.itemName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = item.store.storeName)
                Spacer(modifier = Modifier.size(4.dp))
                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                ){
                    Text(
                        text = formatDate(item.item.date),
                        style = MaterialTheme.typography.bodyLarge)
                }
            }
            Column(modifier = Modifier.padding(8.dp)){
                Text(text = "Qty: ${item.item.qty}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(4.dp))
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        onCheckedChange.invoke(item.item,it)
                    })
            }
        }
    }
}

fun formatDate(date: Date): String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    // Provide a lambda for onNavigate to simulate navigation in the preview
    ListScreen(onNavigate = { /* no-op for preview */ })
}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    MaterialTheme {
        CategoryItem(
            iconRes = android.R.drawable.ic_menu_camera, // Replace with your drawable resource
            title = "Category",
            selected = true,
            onItemClick = { /* Handle item click */ }
        )
    }
}
