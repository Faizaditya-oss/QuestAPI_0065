
package com.faiz.localrestapi_0065

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.faiz.localrestapi_0065.ui.theme.LocalRestAPI_0065Theme
import com.faiz.localrestapi_0065.uicontroller.PetaNavigasi

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            LocalRestAPI_0065Theme{
                PetaNavigasi()
            }
        }
    }
}