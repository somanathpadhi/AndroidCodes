package com.example.completefeature

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.example.completefeature.model.Post
import com.example.completefeature.util.APIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {
    private val mainViewModel: ClothViewModel by viewModels()


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            updateUI()
            LoadData(mainViewModel.postList)
        }
    }

    //Main UI
    fun updateUI(){
        mainViewModel.getPost()
        lifecycleScope.launch{
            mainViewModel.postStateFlow.collect{
                when(it){
                    APIState.Loading -> ""
                    APIState.Empty -> TODO()
                    is APIState.Failure -> TODO()
                    is APIState.Success -> {
                        for (data in it.data){
                            Log.d("data",data.id.toString())
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun LoadData(data: List<Post>){
    LazyColumn(content = {
        items(data){ item ->
            Text(text = item.id.toString())
        }
    })
}
