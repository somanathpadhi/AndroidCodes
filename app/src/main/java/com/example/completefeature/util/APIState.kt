package com.example.completefeature.util

import com.example.completefeature.model.Post

sealed class APIState {
    object Loading: APIState()
    class Success(val data: List<Post>): APIState()
    class Failure(val msg: String): APIState()
    object Empty : APIState()
}