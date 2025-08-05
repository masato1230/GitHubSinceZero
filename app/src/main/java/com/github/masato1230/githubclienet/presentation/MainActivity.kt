package com.github.masato1230.githubclienet.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.core.net.toUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.masato1230.githubclienet.GithubClienetConstants
import com.github.masato1230.githubclienet.presentation.screens.home.HomeScreen
import com.github.masato1230.githubclienet.presentation.screens.userdetail.UserDetailScreen
import com.github.masato1230.githubclienet.presentation.theme.GitHubClienetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubClienetTheme {
                SharedTransitionLayout {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = AppRoute.Home) {
                        composable<AppRoute.Home> {
                            HomeScreen(
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = this@composable,
                                onClickUser = {
                                    navController.navigate(
                                        AppRoute.UserDetail(
                                            userLogin = it.login,
                                            avatarUrl = it.avatarUrl,
                                        )
                                    )
                                },
                            )
                        }

                        composable<AppRoute.UserDetail> { backEntry ->
                            UserDetailScreen(
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = this@composable,
                                onClickBack = {
                                    navController.navigateUp()
                                },
                                onClickXAccount = { accountId ->
                                    val url = "${GithubClienetConstants.X_BASE_URL}/$accountId/"
                                    openCustomTabs(url = url)
                                },
                                onClickBlogLink = {
                                    openCustomTabs(url = it)
                                },
                                onClickRepository = { repository ->
                                    openCustomTabs(url = repository.htmlUrl)
                                },
                                onClickEvent = { event ->
                                    openCustomTabs(url = event.destinationUrl)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun openCustomTabs(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(this, url.toUri())
    }
}