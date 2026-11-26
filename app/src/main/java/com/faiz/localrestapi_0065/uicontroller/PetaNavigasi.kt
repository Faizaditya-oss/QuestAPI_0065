package com.faiz.localrestapi_0065.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.faiz.localrestapi_0065.uicontroller.route.DestinasiEntry
import com.faiz.localrestapi_0065.uicontroller.route.DestinasiHome
import com.faiz.localrestapi_0065.uicontroller.view.EntrySiswaScreen

@Composable
fun DataSiswaApp(navController: NavHostController =
                     rememberNavController(), modifier: Modifier){
    HostNavigasi(navController = navController)
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(navController = navController, startDestination =
        DestinasiHome.route, modifier = Modifier ){
        composable(DestinasiHome.route) {
            HomeScreen(navigateToItemEntry = { navController.navigate
                (DestinasiEntry.route) },

                )
        }
        composable(DestinasiEntry.route){
            EntrySiswaScreen(navigateBack = { navController.navigate
                (DestinasiHome.route) })
        }

    }
}