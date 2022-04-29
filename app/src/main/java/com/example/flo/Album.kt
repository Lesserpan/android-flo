package com.example.flo

import java.util.ArrayList

data class Album(
    val title : String? = "",
    val singer : String? = "",
    var coverImg: Int? = null,
    var songs: ArrayList<Song>? = null
)
