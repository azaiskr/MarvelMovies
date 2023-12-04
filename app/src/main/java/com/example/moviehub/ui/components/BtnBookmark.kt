package com.example.moviehub.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.moviehub.R

@Composable
fun BtnBookmark(
    movieId : String,
    isBookmarked: Boolean,
    onBtnBookmarkClick: (String) -> Unit,
    updateMovieState: (String, Boolean) -> Unit,
) {
    FilledIconButton(
        onClick = {
            onBtnBookmarkClick(movieId)
            updateMovieState(movieId, !isBookmarked)
        },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.DarkGray
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

