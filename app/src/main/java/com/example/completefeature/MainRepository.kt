package com.example.completefeature

import com.example.completefeature.model.Post
import com.example.completefeature.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {
     fun fetchData() = flow {
        emit(apiService.getPosts())
    }.flowOn(Dispatchers.IO)
}