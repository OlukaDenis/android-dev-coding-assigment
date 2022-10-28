package com.domain.utils

import android.os.Build
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import java.io.File
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.format.TextStyle
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * An extension method that extracts time in AM or PM from a provide datetime string
 * @return formatted time string
 */
fun String.toAmPmTime(): String? {
    val tokenizer = StringTokenizer(this)
    val date = tokenizer.nextToken()
    val time = tokenizer.nextToken()

    val sdf = SimpleDateFormat("KK:mm:ss", Locale.getDefault())
    val sdfs = SimpleDateFormat("hh:mm a", Locale.getDefault())

    return try {
        val m = sdf.parse(time)
        sdfs.format(m!!)
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}

fun String.formatContact(): String? {
    val formatted = this.replace("\\s".toRegex(), "")
        .replace("+", "")
    return if (this.length > 9) formatted.takeLast(9) else null
}

fun Double.formatDomainCurrency(): String {
    val format = DecimalFormat("#,###")
    format.isDecimalSeparatorAlwaysShown = false
    return "UGX ${format.format(this)}"
}

fun getMimeType(file: File): String? {
    var type: String? = null
    val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
    if (extension != null) {
        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }
    return type
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLastSeenDate(): String? {

    var convTime: String? = null
    try {
        val timestamp = this.replace(" ", "T")
        val localDateTime = LocalDateTime.parse(timestamp)
        val dow = localDateTime.dayOfWeek
        val timeFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("h:mm a")
            .toFormatter(Locale.ENGLISH)
        val dayFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("MMM d")
            .toFormatter(Locale.ENGLISH)
        val yearFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("MMM d, yyyy")
            .toFormatter(Locale.ENGLISH)


        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
        val pasTime = dateFormat.parse(this)

        val nowTime = System.currentTimeMillis()
        val dateDiff = nowTime - pasTime!!.time
        val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
        val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
        val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
        val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)

        when {
            second < 60 -> {
                convTime = "${second}s"
            }
            minute < 60 -> {
                convTime = "${minute}min"
            }
            hour < 12 -> {
                convTime = localDateTime.format(timeFormatter)
            }
//            hour < 12 -> {
//                convTime = localDateTime.format(timeFormatter)
//            }
            day >= 7 -> {
                convTime = when {
                    day > 360 -> {
                        localDateTime.format(yearFormatter)
                    }
                    day > 30 -> {
                        localDateTime.format(dayFormatter)
                    }
                    else -> {
                        localDateTime.format(dayFormatter)
                    }
                }
            }
            day < 7 -> {
                convTime = dow.getDisplayName(TextStyle.SHORT, Locale.UK)
            }
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return convTime
}