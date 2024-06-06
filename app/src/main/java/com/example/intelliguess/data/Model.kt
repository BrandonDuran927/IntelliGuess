package com.example.intelliguess.data


data class SubjCollection(
    var subject: String,
    var mapPair: MutableMap<String, String>,
    var isEditing: Boolean = false
)
// git hello