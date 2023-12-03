package com.example.moviehub.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.moviehub.R
import com.example.moviehub.injection.Injection
import com.example.moviehub.ui.ViewModelFactory
import com.example.moviehub.ui.screens.content.MovieGridView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    navController: NavHostController,
    searchViewModel: SearchViewModel =
        viewModel(factory = ViewModelFactory(Injection.provideRepository())),
) {
    val query by searchViewModel.query.collectAsState()
    val movieItem by searchViewModel.movieItem.collectAsState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        var active by rememberSaveable {
            mutableStateOf(false)
        }
        SearchBar(
            query = query,
            onQueryChange = searchViewModel::onQueryChange,
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },
            leadingIcon = {
                Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
            },
            placeholder = {
                Text(text = stringResource(R.string.search_bar_hint))
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
        }
        if (query.isNotEmpty()) {
            if (movieItem.isNotEmpty()) {
                MovieGridView(
                    movies = movieItem,
                    navController = navController,
                    updateMovieState = searchViewModel::updateMovieState,
//                    setBookmark = {movieItem ->
//                        searchViewModel.setBookmark(movieItem.movieData.id)
//                    }
                )
            } else {
                OnBoardSearchScreen(
                    message = stringResource(R.string.movie_not_found, query),
                    icon = Icons.Rounded.Warning,
                )
            }
        } else {
            OnBoardSearchScreen(
                message = stringResource(id = R.string.search_view_onboar),
                icon = Icons.Rounded.Search,
            )
        }
    }
}

@Composable
fun OnBoardSearchScreen(
    message: String,
    icon: ImageVector,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = message,
            modifier = Modifier
                .padding(horizontal = 48.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
        )
    }
}
