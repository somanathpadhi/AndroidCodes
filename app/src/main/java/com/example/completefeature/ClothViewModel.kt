package com.example.completefeature

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.completefeature.model.Post
import com.example.completefeature.util.APIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClothViewModel @Inject constructor(private val repository: MainRepository) : ViewModel(){
    private val _postStateFlow: MutableStateFlow<APIState> = MutableStateFlow(APIState.Empty)
    val postStateFlow: StateFlow<APIState> get() = _postStateFlow
    var postList : List<Post> by mutableStateOf(listOf())

    fun getPost() = viewModelScope.launch {
        _postStateFlow.value = APIState.Loading
        repository.fetchData()
            .catch { e->
                _postStateFlow.value = APIState.Failure(e.message.toString())
            }
            .collect{data ->
                _postStateFlow.value = APIState.Success(data)
                postList = data
            }
    }
}