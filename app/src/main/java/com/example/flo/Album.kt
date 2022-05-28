package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.ArrayList

@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey(autoGenerate = false) var id: Int = 0,
    val title : String? = "",
    val singer : String? = "",
    var coverImg: Int? = null
)
