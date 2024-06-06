package com.example.a2020102527_main_exam_project.data.room.models
/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
/*
This code defines three data classes (ShoppingList, Item, and Store) annotated with
 @Entity to represent database tables for Room Persistence Library in Android development,
 facilitating data storage and retrieval.
 */

@Entity(tableName = "shopping_list")
data class ShoppingList(
    @ColumnInfo(name = "list_id")
    @PrimaryKey
    val id: Int,
    val name:String
)

@Entity(tableName = "items")
data class Item(
    @ColumnInfo(name = "item_id")
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val itemName:String,
    val qty:String,
    val listId: Int,
    val storeIdFk:Int,
    val date:Date,
    val isChecked:Boolean
)

@Entity(tableName = "stores")
data class Store(
    @ColumnInfo(name = "store_id")
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val listIdFk:Int,
    val storeName:String
)