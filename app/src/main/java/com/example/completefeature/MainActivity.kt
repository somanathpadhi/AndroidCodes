package com.example.completefeature

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.completefeature.ui.theme.CompleteFeatureTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompleteFeatureTheme {
                MyApp()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent() }
    ) {
        AppContent(drawerState = drawerState, scope = scope)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent() {
    ModalDrawerSheet(
        Modifier.padding(end = 80.dp),
        drawerTonalElevation = 20.dp
    ) {
        Column {
            Text("Drawer title", modifier = Modifier.padding(16.dp))
            Divider()
            NavigationDrawerItem(
                label = { Text(text = "Drawer Item") },
                selected = false,
                onClick = { /* TODO */ },
                Modifier.padding(end = 10.dp)
            )
            // ... other drawer items
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(drawerState: DrawerState, scope: CoroutineScope) {
    Scaffold(
        topBar = { AppTopBar(drawerState = drawerState, scope = scope) }
    ) {
        Column(
            modifier = Modifier.padding(top = 62.dp)
        ) {
            //app content
            openActivity()
        }
    }
}
@Composable
fun openActivity(){
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ){
        Toast.makeText(context, "Hello, Toast!", Toast.LENGTH_SHORT).show()
    }
    fun openSecondActivity() {
        val intent = Intent(context, SecondActivity::class.java)
       // launcher.launch(intent)
        context.startActivity(intent)
    }
    Button(
        onClick = { openSecondActivity()}
    ) {
        Text(text = "Open Activity")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(drawerState: DrawerState, scope: CoroutineScope) {
    Surface(
        shadowElevation = 4.dp,
        tonalElevation = 4.dp
    ) {
        TopAppBar(
            title = { Text(text = "Android Codes") },
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CompleteFeatureTheme {
        MyApp()
    }
}