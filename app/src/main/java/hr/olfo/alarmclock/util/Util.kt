package hr.olfo.alarmclock.util

import android.content.Context
import android.text.format.DateFormat
import android.media.RingtoneManager
import android.net.Uri


object Util {
    fun getDisplayTime(context: Context, h: Int, m: Int): String {
        var displayH = ""
        val displayM = String.format("%02d", m)
        var suffix = ""
        if (!DateFormat.is24HourFormat(context)) {
            suffix = if (h >= 12) {
                displayH = (h - 12).toString()
                " PM"
            } else {
                displayH = h.toString()
                " AM"
            }
        }

        return "$displayH:$displayM$suffix"
    }

    fun getRingtones(context: Context): Map<Uri, String> {
        val rm = RingtoneManager(context)
        rm.setType(RingtoneManager.TYPE_ALARM)

        val alarmsCursor = rm.cursor
        val alarmsCount = alarmsCursor.count
        if (alarmsCount == 0 && !alarmsCursor.moveToFirst()) {
            return mutableMapOf()
        }

        val alarms = mutableMapOf<Uri, String>()
        while (!alarmsCursor.isAfterLast && alarmsCursor.moveToNext()) {
            val currentPosition = alarmsCursor.position
            alarms[rm.getRingtoneUri(currentPosition)] = rm.getRingtone(currentPosition).getTitle(context)
        }

        alarmsCursor.close()
        return alarms
    }
}