package com.thmanyah.data

import com.thmanyah.data.models.HomeResponseDto
import retrofit2.http.GET

interface ApiService {
    @GET("home_sections")
    suspend fun getHomeResponse(): HomeResponseDto
}