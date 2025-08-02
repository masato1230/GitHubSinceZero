package com.github.masato1230.githubclienet.presentation.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
internal class HiltViewModel @Inject constructor(
    private val gitHubHttpClient: HttpClient,
) : ViewModel() {

    init {
        loadSample()
    }

    private val _sampleText = mutableStateOf("Loading")
    val sampleText = _sampleText

    fun loadSample() = viewModelScope.launch {
        val response = gitHubHttpClient.get("users")
        _sampleText.value = response.bodyAsText()
    }
}