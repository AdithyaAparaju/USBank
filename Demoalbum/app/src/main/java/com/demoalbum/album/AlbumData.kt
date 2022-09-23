package com.demoalbum.album

import com.google.gson.annotations.SerializedName

data class AlbumData(
    @SerializedName("userId") val userId: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?
)
