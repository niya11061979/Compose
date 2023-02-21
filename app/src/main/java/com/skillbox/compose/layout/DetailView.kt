package com.skillbox.compose.layout

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import com.skillbox.compose.data.DetailWithEpisode
import com.skillbox.compose.data.Episode

@ExperimentalMaterialApi
@Composable
fun DetailView(navController: NavController, detailWithEpisode: DetailWithEpisode) {
    Scaffold(
        backgroundColor = Color.Black,
        topBar = { Appbar(navController, "Person details") },
        content = { DetailsCharacter(detailWithEpisode) }
    )
}

@ExperimentalMaterialApi
@Composable
fun DetailsCharacter(detailWithEpisode: DetailWithEpisode) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        detailWithEpisode.detailCharacter.posterUrl?.let { url ->
            val image = loadImage(url = url, defaultImage = ERROR_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "Image character",
                    modifier = Modifier
                        .padding(4.dp)
                        .height(220.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(start = 24.dp),
            text = detailWithEpisode.detailCharacter.name,
            maxLines = 2,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White.copy(alpha = 0.8f)
        )
        Divider(
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        DescriptionColumnText(descriptionText = "Live status:")
        Row {
            Canvas(modifier = Modifier
                .padding(start = 24.dp, top = 9.dp)
                .size(6.dp), onDraw = {
                if (detailWithEpisode.detailCharacter.status == "Alive") drawCircle(color = Color.Green)
                else drawCircle(color = Color.Red)
            })
            ColumnText(detailWithEpisode.detailCharacter.status)
        }

        Spacer(modifier = Modifier.height(16.dp))
        DescriptionColumnText(descriptionText = "Species and gender:")
        val species = detailWithEpisode.detailCharacter.species
        val gender = detailWithEpisode.detailCharacter.gender
        ColumnText(columnText = "$species($gender)")
        Spacer(modifier = Modifier.height(16.dp))
        DescriptionColumnText("Last known location:")
        ColumnText(columnText = detailWithEpisode.detailCharacter.location.name)
        Spacer(modifier = Modifier.height(16.dp))
        DescriptionColumnText("First seen in:")
        if (detailWithEpisode.episode.isNotEmpty()) {
            val episodeTitle=detailWithEpisode.episode[0].name
            ColumnText(columnText =episodeTitle)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier.padding(start = 24.dp),
            text = "Episodes:",
            maxLines = 1,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White.copy(alpha = 0.8f)
        )

        Row(Modifier.horizontalScroll(rememberScrollState())) {
            if (detailWithEpisode.episode.isNotEmpty()) detailWithEpisode
                .episode.forEach { episode -> EpisodeItem(episode) }
        }
    }
}

@Composable
fun ColumnText(columnText: String) {
    Text(
        modifier = Modifier.padding(start = 24.dp, end = 8.dp),
        text = columnText,
        maxLines = 1,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White.copy(alpha = 0.8f)
    )
}

@Composable
fun DescriptionColumnText(descriptionText: String) {
    Text(
        modifier = Modifier.padding(start = 24.dp, end = 8.dp),
        text = descriptionText,
        maxLines = 1,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White.copy(alpha = 0.6f)
    )
}

@Composable
fun EpisodeItem(episode: Episode) {
    Card(
        elevation = 12.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight(),
        backgroundColor = Color.DarkGray

    ) {
        Row {
            Column {
                ColumnText(columnText = episode.name)
                DescriptionColumnText(episode.airDate)
            }
            DescriptionColumnText(episode.episode)
        }
    }
}
