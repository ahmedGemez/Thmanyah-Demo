package com.thmanyah.thmanyahdemo.ui.utils

import androidx.annotation.StringRes
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import com.thmanyah.thmanyahdemo.R
import com.thmanyah.thmanyahdemo.ui.models.ThmanyahError
import com.thmanyah.thmanyahdemo.ui.models.UiState
import retrofit2.HttpException
import timber.log.Timber
import java.io.EOFException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun <T> T.toUiState(): UiState<T> = when {
    else -> UiState.Success(this)
}

fun <T> Throwable.toUiState(): UiState<T> =
    UiState.Error(toThmanyahError())

fun Throwable.toThmanyahError(): ThmanyahError {
    Timber.e(this)
    return when (this) {
        is HttpException -> {
            val response = this.response()
            val error = response?.errorBody()
            val httpCode = response?.code() ?: ErrorCode.UNKNOWN
            val errorBody = when {
                error == null || error.contentLength() == 0L -> null
                else -> try {
                    val json = error.string().also { Timber.e(it) }
                    when {
                        json.toThmanyahError().message != null -> json.toThmanyahError()
                        else -> getDefaultError(httpCode)
                    }
                } catch (e: JsonSyntaxException) {
                    Timber.e(e, "convert error Exception")
                    null
                }
            }
            getError(httpCode, errorBody)
        }
        is MalformedJsonException,
//    is JsonParseException,
        is EOFException,
        -> getDefaultError()
        is SocketTimeoutException,
        is UnknownHostException,
        is ConnectException,
        is IOException,
        -> (this as IOException).toThmanyahError()
        is JsonParseException -> getDefaultError()
        else -> getDefaultError()
    }
}


fun IOException.toThmanyahError() = ThmanyahError(
    code = ErrorCode.NETWORK,
    messageRes = R.string.no_internet_connection
)
private fun String.toThmanyahError() = toObjectOrThrow(ThmanyahError::class.java)

fun <T> String?.toObjectOrThrow(classOfT: Class<T>): T =
    toObject(classOfT)
        ?: throw JsonSyntaxException("Error in converting json to object!")

fun <T> String?.toObject(classOfT: Class<T>): T? = Gson().fromJson(this, classOfT)


fun getDefaultError(
    httpCode: Int = ErrorCode.UNKNOWN,
    message: String? = null,
    messageRes: Int = R.string.error_1
) = ThmanyahError(
    code = httpCode,
    message = message,
    messageRes = messageRes
)


private fun getError(
    httpCode: Int,
    anonymous: Any?,
): ThmanyahError {
    return when (anonymous) {
        is ThmanyahError -> anonymous
        else -> getDefaultError(httpCode)
    }
}