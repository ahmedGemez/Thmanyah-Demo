package com.thmanyah.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T> Flow<T>.mapDataOrThrow(): Flow<T> =
    map {
        it.orThrow()
    }