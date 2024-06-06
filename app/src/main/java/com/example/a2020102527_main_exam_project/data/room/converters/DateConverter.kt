package com.example.a2020102527_main_exam_project.data.room.converters
/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/
import androidx.room.TypeConverter
import java.util.Date

/*
This code defines a DateConverter class with two methods
 annotated with @TypeConverter for converting between Long timestamps and Date objects,
  facilitating data conversion for Room Persistence Library in Android development.
 */
open class DateConverter{
    @TypeConverter
    fun toDate(date: Long?): Date?{
        return date?.let{ Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long?{
        return date?.time
    }
}