package com.example.nycschools.Network

import com.example.nycschools.Model.Schools
import com.example.nycschools.Model.Scores
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET(SCHOOLS)
    suspend fun getAllSchools(): Response<List<Schools>>

    @GET(SCORES)
    suspend fun getSATScores(
        @Query("dbn") dbn : String
    ): Response<List<Scores>>


    companion object {
        const val BASE_URL = "https://data.cityofnewyork.us/resource/"
        private const val SCHOOLS = "s3k6-pzi2.json"
        private const val SCORES = "f9bf-2cp4.json"
    }
}