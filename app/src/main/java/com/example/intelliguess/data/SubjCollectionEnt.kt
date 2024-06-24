package com.example.intelliguess.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

// Entity of my database
@Entity
data class SubjCollectionEnt (
    var subject: String,
    var mapPair: MutableMap<String, String>,
    var isEditing: Boolean = false,

    //Automatically create a numbering of ID
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
