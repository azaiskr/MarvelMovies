package com.example.moviehub.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviehub.injection.Injection
import com.example.moviehub.model.MovieItem
import com.example.moviehub.ui.UiState
import com.example.moviehub.ui.ViewModelFactory
import com.example.moviehub.ui.components.BtnBookmark
import com.example.moviehub.ui.screens.content.ErrorScreen
import com.example.moviehub.ui.screens.content.LoadingScreen

@Composable
fun DetailScreen(
    movieId: String,
    modifier: Modifier,
    detailViewModel: DetailViewModel =
        viewModel(factory = ViewModelFactory(Injection.provideRepository())),
) {
    detailViewModel.detailUiState.collectAsState(
        initial = UiState.Loading
    ).value.let { detailUiState ->
        when (detailUiState) {
            is UiState.Loading -> {
                LoadingScreen(modifier = modifier)
                detailViewModel.getMovieDetail(movieId)
            }

            is UiState.Success -> {
                val movie = detailUiState.data
                DetailContent(
                    movieDetail = movie,
                    modifier = modifier,
                    updateMovieState = {movieId, isBookmarked ->
                        detailViewModel.updateMovieState(movieId, isBookmarked)
                    }
                )
            }

            is UiState.Error -> ErrorScreen(
                retryAction = { detailViewModel.getMovieDetail(movieId) },
                modifier = modifier,
            )
        }

    }

}

@Composable
fun DetailContent(
    movieDetail: MovieItem,
    modifier: Modifier = Modifier,
    updateMovieState: (String, Boolean) -> Unit,
//    setBookmark: (isBookmark: Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()
    var isBookmarked by rememberSaveable {
        mutableStateOf(movieDetail.isBookmarked)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp, 0.dp)
            .verticalScroll(
                state = scrollState,
                enabled = true,
                reverseScrolling = true,
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(movieDetail.movieData.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(32.dp)
            ) {
                BtnBookmark(
                    isBookmarked = isBookmarked,
                    movieId = movieDetail.movieData.id,
                    onBtnBookmarkClick = { isBookmarked = !isBookmarked },
                    updateMovieState = updateMovieState,
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(0.dp, 0.dp, 16.dp, 8.dp))
        ) {
            Text(
                text = movieDetail.movieData.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                softWrap = true,
                lineHeight = 32.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = movieDetail.movieData.releaseDate,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(end = 8.dp)
            )
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .height(16.dp),
            )
            Text(
                text = movieDetail.movieData.runtime,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
        }

        Text(
            text = "Overview",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 8.dp),
        )
        Text(
            text = movieDetail.movieData.overview,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
