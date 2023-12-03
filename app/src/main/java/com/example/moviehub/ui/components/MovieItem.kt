package com.example.moviehub.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviehub.R
import com.example.moviehub.model.MovieItem

@Composable
fun MovieItem(
    onClick: () -> Unit,
    movie: MovieItem,
    updateMovieState: (String, Boolean) -> Unit,
) {
    var isBookmarked by rememberSaveable {
        mutableStateOf(movie.isBookmarked)
    }

    Card(
        modifier = Modifier
            .size(width = 160.dp, height = 260.dp)
            .clickable(onClick = onClick)
            .wrapContentHeight(),
    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(movie.movieData.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.hide_image),
                contentDescription = stringResource(R.string.movie_poster),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(32.dp)
            ) {
                BtnBookmark(
                    isBookmarked = movie.isBookmarked,
                    movieId = movie.movieData.id,
                    onBtnBookmarkClick = {isBookmarked = !isBookmarked},

                    updateMovieState = updateMovieState,
                )
            }
        }
    }
}