package com.example.moviehub.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviehub.R
import com.example.moviehub.ui.navigation.Screen
import com.example.moviehub.ui.theme.Maroon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieHubTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    TopAppBar(
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(Maroon.value),
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
        ),
        title = {

            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold
            )
        },
        actions = {
            IconButton(
                onClick = {
                    if (currentRoute != Screen.Profile.route) {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(stringResource(R.string.iv_profile_url))
                        .build(),
                    contentDescription = stringResource(R.string.profile_image),
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        },
    )
}


/*
    === BEST PRACTICE ===

    interface NavigationCallback {
        fun navigateToProfile()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MovieHubTopBar(
        scrollBehavior: TopAppBarScrollBehavior,
        navigationCallback: NavigationCallback // Pass the callback as a parameter
    ) {
        // ... (existing code)

        actions = {
            IconButton(
                onClick = {
                    if (currentRoute != Screen.Profile.route) {
                        navigationCallback.navigateToProfile() // Call the callback when the button is clicked
                    }
                }
            ) {
                // ... (existing code)
            }
        }
        // ... (existing code)
    }


    class MovieHubScreen(
        private val navController: NavHostController
    ) : NavigationCallback {

        // ... (other methods and properties)

        // Implementation of the callback interface
        override fun navigateToProfile() {
            navController.navigate(Screen.Profile.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }

        // ... (rest of the class)
    }


 */