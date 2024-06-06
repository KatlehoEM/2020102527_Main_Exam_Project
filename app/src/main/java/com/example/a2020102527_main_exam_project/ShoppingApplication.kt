package com.example.a2020102527_main_exam_project
/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/

//This code initializes the dependency injection graph for the application when the app is created.
import android.app.Application

class ShoppingApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}