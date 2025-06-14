package com.thmanyah.data

import com.thmanyah.data.models.HomeResponseDto
import com.thmanyah.data.models.SearchResponseDto
import retrofit2.http.GET

interface ApiService {
    @GET("home_sections")
    suspend fun getHomeResponse(): HomeResponseDto

    @GET("https://mock.apidog.com/m1/735111-711675-default/search")
    suspend fun search(): SearchResponseDto
}