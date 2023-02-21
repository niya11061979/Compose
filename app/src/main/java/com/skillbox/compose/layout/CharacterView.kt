package com.skillbox.compose.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.skillbox.compose.bottomnavigation.BottomNavigationBar
import com.skillbox.compose.data.Characters
import com.skillbox.compose.data.Location
import com.skillbox.compose.fragment.CharacterListFragmentDirections
import com.skillbox.compose.state.ErrorItem
import com.skillbox.compose.state.LoadingItem
import com.skillbox.compose.state.LoadingView
import com.skillbox.compose.viewmodel.CharacterListViewModel
import kotlinx.coroutines.flow.Flow

@ExperimentalMaterialApi
@Composable
fun CharacterView(navController: NavController, viewModel: CharacterListViewModel) {
    Scaffold(
        backgroundColor = Color.Black,
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black,
                title = { Text(text = "Rick and Morty", color = Color.White) }
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
        content = { CharacterList(navController, character = viewModel.characterPagingFlow) }

    )
}


@ExperimentalMaterialApi
@Composable
fun CharacterList(navController: NavController, character: Flow<PagingData<Characters>>) {
    val lazyCharacters = character.collectAsLazyPagingItems()
    LazyColumn {
        items(lazyCharacters) { character ->
            character?.let { CharacterItem(character = character, navController) }
                ?: Text(text = "Ошибка")
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

@ExperimentalMaterialApi
@Composable
fun CharacterItem(
    character: Characters,
    navController: NavController
) {
    Card(
        onClick = {
            navController
                .navigate(CharacterListFragmentDirections.actionToDetailFragment(character.id))
        },
        elevation = 12.dp,
        modifier = Modifier.padding(8.dp),
        backgroundColor = Color.DarkGray

    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                character.posterUrl?.let { url ->
                    val image = loadImage(url = url, defaultImage = ERROR_IMAGE).value
                    image?.let { img ->
                        Image(
                            bitmap = img.asImageBitmap(),
                            contentDescription = "Image character",
                            modifier = Modifier.size(100.dp),
                            contentScale = ContentScale.Fit,
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    CharacterName(character.name, modifier = Modifier)
                    Row {
                        CharacterStatus(
                            "${character.status}(${character.species})",
                            modifier = Modifier
                        )
                    }
                    CharacterLocationText(modifier = Modifier)
                    CharacterLocation(character.location)

                }
            }
        }

    }
}


@Composable
fun CharacterName(name: String, modifier: Modifier) {
    Text(
        modifier = modifier.padding(top = 8.dp),
        text = name,
        maxLines = 1,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White.copy(alpha = 0.8f)
    )
}

@Composable
fun CharacterStatus(status: String, modifier: Modifier) {
    Text(
        modifier = modifier.padding(top = 6.dp, end = 10.dp),
        text = status,
        maxLines = 1,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White.copy(alpha = 0.6f)
    )

}

@Composable
fun CharacterLocationText(modifier: Modifier) {
    Text(
        modifier = modifier.padding(top = 12.dp),
        text = "Last known location:",
        maxLines = 1,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White.copy(alpha = 0.6f)
    )

}

@Composable
fun CharacterLocation(location: Location) {
    Text(
        modifier = Modifier,
        text = location.name,
        maxLines = 1,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White.copy(alpha = 0.8f)
    )
}
