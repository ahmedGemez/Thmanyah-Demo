package com.thmanyah.thmanyahdemo.ui.models

import com.google.gson.annotations.SerializedName

data class ThmanyahError(
    val code: Int? = null,
    val message: String? = null,
    val messageRes: Int? = null,
)
