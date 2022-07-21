package com.example.nycschools.Network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface RepositoryLayer {
    fun getAllNycSchools(): Flow<NetworkState>
    fun getSchoolSATScores(dbn: String): Flow<NetworkState>
}

class RepositoryLayerImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : RepositoryLayer {

    override fun getAllNycSchools(): Flow<NetworkState> =
        flow {
            emit(NetworkState.LOADING())

            try {
                val response = serviceApi.getAllSchools()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(NetworkState.SUCCESS(it))
                    } ?: throw Exception("Null body in the response")
                } else {
                    throw Exception("Failure response")
                }
            } catch (e: Exception) {
                emit(NetworkState.ERROR(e))
            }
        }


    override fun getSchoolSATScores(dbn: String): Flow<NetworkState> =
        flow {
            emit(NetworkState.LOADING())

            try {
                val response = serviceApi.getSATScores(dbn)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(NetworkState.SUCCESS(it))
                    } ?: throw Exception("Null body in the response")
                } else {
                    throw Exception("Failure response")
                }
            } catch (e: Exception) {
                emit(NetworkState.ERROR(e))
            }
        }
}