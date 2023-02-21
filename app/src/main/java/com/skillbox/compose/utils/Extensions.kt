package com.skillbox.compose.utils

import android.annotation.SuppressLint
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
fun formatDate(isoData: String): String {
    val actual =
        OffsetDateTime.parse(isoData, DateTimeFormatter.ISO_DATE_TIME)
    val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
    return actual.format(formatter)

}