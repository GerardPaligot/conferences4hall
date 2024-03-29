package org.gdglille.devfest.android

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.gdglille.devfest.repositories.AgendaRepository
import org.koin.core.component.KoinComponent

class ScheduleWorkManager(
    context: Context,
    parameters: WorkerParameters,
    private val repository: AgendaRepository
) :
    CoroutineWorker(context, parameters), KoinComponent {
    override suspend fun doWork(): Result {
        return try {
            repository.fetchAndStoreAgenda()
            Result.success()
        } catch (_: Throwable) {
            Result.failure()
        }
    }
}
