package com.skillbox.compose.layout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.skillbox.compose.bottomnavigation.BottomNavigationBar
import com.skillbox.compose.data.Locations
import com.skillbox.compose.state.ErrorItem
import com.skillbox.compose.state.LoadingItem
import com.skillbox.compose.state.LoadingView
import com.skillbox.compose.utils.formatDate
import com.skillbox.compose.viewmodel.LocationListViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun LocationsView(navController: NavController, viewModel: LocationListViewModel) {
    Scaffold(
        backgroundColor = Color.Black,
        topBar = { Appbar(navController, "Locations") },
        bottomBar = { BottomNavigationBar(navController) },
        content = { LocationsList(locations = viewModel.locationPagingFlow) }
    )

}

@Composable
fun LocationsList(locations: Flow<PagingData<Locations>>) {
    val lazyCharacters = locations.collectAsLazyPagingItems()
    LazyColumn {
        items(lazyCharacters) { location ->
            location?.let { LocationItem(locations = location) } ?: Text(text = "Ошибка")
        }

        lazyCharacters.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyCharacters.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = lazyCharacters.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun LocationItem(locations: Locations) {
    Card(
        elevation = 12.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        backgroundColor = Color.DarkGray

    ) {
        Row {
            locations.name
            Column {
                val date = formatDate(locations.created)
                ColumnText(columnText = locations.name)
                DescriptionColumnText(date)
            }
        }
    }
}


