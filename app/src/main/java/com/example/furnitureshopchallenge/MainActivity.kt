package com.example.furnitureshopchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.furnitureshopchallenge.data.datasource.local.Constants
import com.example.furnitureshopchallenge.data.datasource.local.SharedPref
import com.example.furnitureshopchallenge.presentation.login.LoginScreen
import com.example.furnitureshopchallenge.presentation.login.LoginViewModel
import com.example.furnitureshopchallenge.presentation.warehouses.WarehouseScreen
import com.example.furnitureshopchallenge.presentation.warehouses.WarehouseViewModel
import com.example.furnitureshopchallenge.ui.theme.FurnitureShopChallengeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FurnitureShopChallengeTheme {
                    val navController = rememberNavController()
                val loginViewModel = hiltViewModel<LoginViewModel>()
                NavHost(navController, startDestination = islogged()) {
                    composable(route = "login") {
                        LoginScreen(navController, loginViewModel)
                    }
                    composable(route = "warehouse") {
                        WarehouseScreen(navController)
                    }
                }

            }
        }
    }

    private fun islogged() : String{
        val sharedPref = SharedPref(applicationContext)
        val user = sharedPref.getFromPrefs(Constants.LOGGED_USER)
        if (user != null){
            return "warehouse"
        }else{
            return "login"
        }
    }
}


