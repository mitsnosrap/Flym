package net.frju.flym.data.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import net.frju.flym.App
import net.frju.flym.ui.main.MainActivity
import java.lang.IllegalArgumentException

fun setTeslaUnreadCount() {
    Thread(Runnable {
        // Sets unread count badge using TeslaUnread
        // http://novalauncher.com/teslaunread-api/
        try {
            val unreadCount = App.db.entryDao().countUnread
            val contentValues = ContentValues()
            contentValues.put("tag", App.context.packageName + "/" + MainActivity::class.java.canonicalName)
            contentValues.put("count", unreadCount)

            val uri = Uri.parse("content://com.teslacoilsw.notifier/unread_count")
            App.context.contentResolver.insert(uri, contentValues)

        } catch (exception: IllegalArgumentException) {
            // Occurs when TeslaUnread is not installed
        } catch (exception: Exception) {
            // Some other error
            exception.printStackTrace()
        }
    }).start()
}