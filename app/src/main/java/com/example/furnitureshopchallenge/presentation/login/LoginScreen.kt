package com.example.furnitureshopchallenge.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.furnitureshopchallenge.R
import kotlinx.coroutines.launch



@Composable
fun LoginScreen(navHostController: NavHostController, viewModel: LoginViewModel) {

    val coroutineScope = rememberCoroutineScope()

    suspend fun login(email: String, pass: String) {
        viewModel.Login(email, pass).collect {
            if (it != null) {
                viewModel.saveUserToPref(email)
                viewModel.getRole
                navHostController.navigate(
                    "warehouse"){popUpTo("warehouse") { inclusive = true }
                }
            }
        }
    }


    val username = remember {
        mutableStateOf(TextFieldValue())
    }
    val password = remember {
        mutableStateOf(TextFieldValue())
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        val (button, image, email, pass) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.furniture),
            contentDescription = "furnitureImage",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                }
                .height(300.dp)
                .fillMaxWidth()
        )


        TextField(
            label = { Text(text = "Email") },
            value = username.value,
            onValueChange = { username.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.constrainAs(email) {
                top.linkTo(image.bottom, margin = 40.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            label = { Text(text = "Password") },
            value = password.value,
            onValueChange = { password.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.constrainAs(pass) {
                top.linkTo(email.bottom, margin = 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )


        Box(modifier = Modifier
            .padding(40.dp, 0.dp, 40.dp, 0.dp)
            .constrainAs(button) {
                top.linkTo(pass.bottom, margin = 30.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        login(username.value.text, password.value.text)
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Login")
            }
        }

    }
}


