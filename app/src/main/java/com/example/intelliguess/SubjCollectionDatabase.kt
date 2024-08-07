package com.example.intelliguess

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.intelliguess.data.SubjCollectionEnt


// Serves as the database of this application using roomDB
@Database(
    entities = [SubjCollectionEnt::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class SubjCollectionDatabase : RoomDatabase() {
    abstract val dao: SubjCollectionDao
}