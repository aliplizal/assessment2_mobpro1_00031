package com.aliplizal607062300031.assessment2.navigation

import com.aliplizal607062300031.assessment2.ui.screen.KEY_ID_BUKU


sealed class Screen(val route: String){
    data object  Home: Screen("mainScreen")
    data object  FormBaru: Screen("detailScreen")
    data object  FormUbah: Screen("detailScreen/{$KEY_ID_BUKU}"){
        fun withId(id: Long) = "detailScreen/$id"
    }
}