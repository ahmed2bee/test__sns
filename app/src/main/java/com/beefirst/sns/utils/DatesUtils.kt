package com.beefirst.sns.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DatesUtils {

    companion object {
         fun convertDateTime(date: String): String {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val date: Date = format.parse(date)
            return date.toString()
        }
    }
}