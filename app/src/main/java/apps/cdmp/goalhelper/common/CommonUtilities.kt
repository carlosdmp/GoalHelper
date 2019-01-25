package apps.cdmp.goalhelper.common

import java.util.*
import java.util.concurrent.TimeUnit

fun doNothing() {}

private fun Date.diffInDaysTo(other: Date): Long {
    val diffInMillis = Math.abs(other.time - time)
    return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS)
}

infix fun Date.remainingTo(other: Date) = (diffInDaysTo(other) + 1).toString()

