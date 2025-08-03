package com.github.masato1230.githubclienet.presentation.utils

import java.time.format.DateTimeFormatter
import java.util.Locale

object CustomDateFormatters {

    val defaultDateTimeFormatter =
        DateTimeFormatter.ofPattern("MM dd, yyyy HH:mm:ss", Locale.JAPAN)
}