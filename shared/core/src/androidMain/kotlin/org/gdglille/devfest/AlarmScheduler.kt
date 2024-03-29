package org.gdglille.devfest

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.SystemClock
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.gdglille.devfest.android.shared.resources.Resource
import org.gdglille.devfest.android.shared.resources.title_notif_reminder_talk
import org.gdglille.devfest.models.ui.TalkItemUi
import org.gdglille.devfest.repositories.AgendaRepository
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getString
import java.util.Locale

class AlarmScheduler(
    private val repository: AgendaRepository,
    private val alarmManager: AlarmManager,
    private val alarmIntentFactory: AlarmIntentFactory
) {
    @OptIn(ExperimentalResourceApi::class)
    @SuppressLint("UnspecifiedImmutableFlag")
    suspend fun schedule(context: Context, talkItem: TalkItemUi) = coroutineScope {
        val isFavorite = !talkItem.isFavorite
        repository.markAsRead(talkItem.id, isFavorite)
        val title = getString(
            Resource.string.title_notif_reminder_talk,
            talkItem.room.lowercase(Locale.getDefault())
        )
        val intent = alarmIntentFactory.create(context, talkItem.id, title, talkItem.title)
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent =
            PendingIntent.getBroadcast(context, talkItem.id.hashCode(), intent, flags)
        if (isFavorite) {
            val time =
                talkItem.startTime.toLocalDateTime().toInstant(TimeZone.currentSystemDefault())
                    .minus(ReminderInMinutes, DateTimeUnit.MINUTE).toEpochMilliseconds()
            alarmManager.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + (time - Clock.System.now().toEpochMilliseconds()),
                pendingIntent
            )
        } else {
            alarmManager.cancel(pendingIntent)
        }
    }

    companion object {
        private const val ReminderInMinutes = 10
    }
}
