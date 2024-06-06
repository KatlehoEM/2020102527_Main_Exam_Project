package com.example.a2020102527_main_exam_project.ui.list
/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2020102527_main_exam_project.Graph
import com.example.a2020102527_main_exam_project.data.repository.Repository
import com.example.a2020102527_main_exam_project.data.room.ItemsWithStoreAndList
import com.example.a2020102527_main_exam_project.data.room.models.Item
import com.example.a2020102527_main_exam_project.ui.Category
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
/*
This code defines a ViewModel class ListViewModel responsible for managing
the state and business logic related to displaying a list of items.
 It also includes a data class ListState to hold the state of the list screen.
 */
class ListViewModel(
    private val repository: Repository = Graph.repository
): ViewModel() {
    var state by mutableStateOf(ListState())
        private set

    init{
        getItems()
    }

    private fun getItems(){
        viewModelScope.launch{
            repository.getItemsWithListAndStore.collectLatest {
                state = state.copy(
                    items = it
                )
            }
        }
    }

    fun deleteItem(items: Item){
        viewModelScope.launch {
            repository.deleteItem(items)
        }
    }

    fun onCategoryChange(category: Category){
        state = state.copy(category = category)
        filterBy(category.id)
    }

    fun onItemCheckedChange(item: Item, isChecked :Boolean){
        viewModelScope.launch {
            repository.updateItem(
                item = item.copy(isChecked = isChecked)
            )
        }
    }

    private fun filterBy(shoppingListId: Int){
        if(shoppingListId != 10001){
            viewModelScope.launch {
                repository.getItemsWithStoreWithListFilteredById(
                    shoppingListId
                ).collectLatest {
                    state = state.copy(items = it)
                }
            }
        }else{
            getItems()
        }
    }

}
data class ListState(
    val items: List<ItemsWithStoreAndList> = emptyList(),
    val category: Category = Category(),
    val itemChecked: Boolean = false
)