package com.example.a2020102527_main_exam_project.data.room
/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.a2020102527_main_exam_project.data.room.converters.DateConverter
import com.example.a2020102527_main_exam_project.data.room.models.Item
import com.example.a2020102527_main_exam_project.data.room.models.ShoppingList
import com.example.a2020102527_main_exam_project.data.room.models.Store

/*
This code defines a Room database class ShoppingListDatabase with
 three DAO access methods (listDao(), itemDao(), storeDao()), incorporating
 TypeConverters for date conversion and declaring database entities.
 It also provides a companion object with a method for getting an instance of the database,
  ensuring thread safety with double-checked locking.
 */
@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [ShoppingList::class, Item::class, Store::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingListDatabase:RoomDatabase() {
    abstract fun listDao():ListDao
    abstract fun itemDao():ItemDao
    abstract fun storeDao():StoreDao

    companion object{
        @Volatile
        var INSTANCE:ShoppingListDatabase? = null
        fun getDatabase(context: Context): ShoppingListDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    ShoppingListDatabase::class.java,
                    "shopping_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}