package com.example.a2020102527_main_exam_project.data.repository
/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/
import com.example.a2020102527_main_exam_project.data.room.ItemDao
import com.example.a2020102527_main_exam_project.data.room.ListDao
import com.example.a2020102527_main_exam_project.data.room.StoreDao
import com.example.a2020102527_main_exam_project.data.room.models.Item
import com.example.a2020102527_main_exam_project.data.room.models.ShoppingList
import com.example.a2020102527_main_exam_project.data.room.models.Store

/*
The code defines a repository class that handles data access and manipulation,
providing methods for inserting, deleting, and updating items, stores, and shopping lists,
as well as functions to retrieve items with associated stores and lists.
 */

class Repository(
    private val listDao: ListDao,
    private val storeDao: StoreDao,
    private val itemDao: ItemDao
) {
    val store = storeDao.getAllStores()
    val getItemsWithListAndStore = listDao.getItemsWithStoreAndList()

    fun getItemWithStoreAndList(id:Int) = listDao
        .getItemWithStoreAndListFilteredById(id)

    fun getItemsWithStoreWithListFilteredById(id:Int) = listDao
        .getItemsWithStoreAndListFilteredById(id)

    suspend fun insertList(shoppingList: ShoppingList){
        listDao.insertShoppingList(shoppingList)
    }

    suspend fun insertStore(store: Store){
        storeDao.insert(store)
    }

    suspend fun insertItem(item: Item){
        itemDao.insert(item)
    }

    suspend fun deleteItem(item:Item){
        itemDao.delete(item)
    }

    suspend fun updateItem(item:Item){
        itemDao.update(item)
    }
}