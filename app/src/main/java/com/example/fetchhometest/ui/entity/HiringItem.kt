package com.example.fetchhometest.ui.entity

import com.google.gson.annotations.SerializedName

data class HiringItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("listId")
    val listId: Int,
    @SerializedName("name")
    val name: String?
)

