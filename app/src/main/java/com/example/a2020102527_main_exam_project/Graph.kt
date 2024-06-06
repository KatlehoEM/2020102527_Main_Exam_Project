package com.example.a2020102527_main_exam_project
/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/
import android.content.Context
import com.example.a2020102527_main_exam_project.data.repository.Repository
import com.example.a2020102527_main_exam_project.data.room.ShoppingListDatabase

/*
The Graph object represents a singleton object in Kotlin.
It initializes a ShoppingListDatabase instance lazily and provides a repository
instance using the database DAOs. It ensures that the database is initialized before accessing its DAOs.
The provide function is responsible for initializing the database with a given context.
 */
object Graph {
    lateinit var db: ShoppingListDatabase
        private set

    val repository by lazy{
        Repository(
            listDao = db.listDao(),
            storeDao = db.storeDao(),
            itemDao = db.itemDao()
        )
    }


    fun provide(context: Context){
        db = ShoppingListDatabase.getDatabase(context)
    }
}