package apps.cdmp.goalhelper.common

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


var dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
fun doNothing() {}

const val gregorianCalendarOffset = 1900

infix fun Date.diffTo(other: Date): Long {
    val diffInMillies = Math.abs(other.time - time)
    return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
}

infix fun Date.remainingTo(other: Date): String {
    return diffTo(other).toString()
}