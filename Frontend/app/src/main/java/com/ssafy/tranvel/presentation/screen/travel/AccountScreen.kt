package com.ssafy.tranvel.presentation.screen.travel

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.ssafy.tranvel.presentation.screen.travel.component.AccountBody
import com.ssafy.tranvel.presentation.screen.travel.component.GameBody
import com.ssafy.tranvel.presentation.screen.travel.component.GameHeader

@Composable
fun AccountScreen(
    onBackPressed: () -> (Unit)
) {
    Scaffold(
        topBar = { GameHeader("비용 추가", false) },
        content = { paddingValues ->
            Column {
                AccountBody(paddingValues){
                    onBackPressed()
                }
            }
        },
    )
}