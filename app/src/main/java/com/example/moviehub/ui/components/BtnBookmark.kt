package com.example.moviehub.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.moviehub.R
import com.example.moviehub.model.MovieItem

@Composable
fun BtnBookmark(
    setBookmark: (MovieItem) -> Unit,
    isBookmarked: Boolean,
    movieItem: MovieItem,
) {
    FilledIconButton(
        onClick = { setBookmark(movieItem) },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = CircleShape,
    ) {
        Icon(
            painter = if (isBookmarked) {
                painterResource(id = R.drawable.baseline_bookmark)
            } else {
                painterResource(id = R.drawable.bookmark_border)
            },
            contentDescription = "add to bookmark",
            tint = Color.Yellow,
        )
    }
}

