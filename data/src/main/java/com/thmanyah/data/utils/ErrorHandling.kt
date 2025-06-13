package com.thmanyah.data.utils

import com.google.gson.JsonParseException

fun <T> T?.orThrow(
    message: String? = null,
    exception: Exception = JsonParseException(message),
): T = this ?: throw exception

