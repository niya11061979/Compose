package com.skillbox.compose.bottomnavigation

import com.skillbox.compose.R

sealed class NavigationItem(var route: Int, var icon: Int, var title: String) {
    object Characters :
        NavigationItem(R.id.characterListFragment, R.drawable.rick_morty, "Characters")

    object Locations : NavigationItem(R.id.locationsListFragment, R.drawable.location, "Locations")
}
