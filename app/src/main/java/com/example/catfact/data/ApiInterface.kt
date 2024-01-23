package com.example.catfact.data

import com.example.catfact.models.ApiData
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("")
    suspend fun getRandomFact():Response<ApiData>

}