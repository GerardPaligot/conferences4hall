package org.gdglille.devfest.android

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import io.openfeedback.viewmodels.OpenFeedbackFirebaseConfig
import io.openfeedback.viewmodels.initializeOpenFeedback
import org.gdglille.devfest.android.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

private const val MemoryCacheSize = 0.25
private const val DiskCacheSize = 0.10

class MainApplication : Application(), ImageLoaderFactory, KoinComponent {
    override fun onCreate() {
        super.onCreate()
        initializeOpenFeedback(
            OpenFeedbackFirebaseConfig(
                context = this,
                projectId = BuildConfig.FIREBASE_PROJECT_ID,
                applicationId = BuildConfig.APPLICATION_ID,
                apiKey = BuildConfig.FIREBASE_API_KEY,
                databaseUrl = "https://${BuildConfig.FIREBASE_PROJECT_ID}.firebaseio.com"
            )
        )

        startKoin {
            androidContext(this@MainApplication)
            workManagerFactory()
            modules(appModule)
        }
    }

    override fun newImageLoader(): ImageLoader = ImageLoader.Builder(this)
        .components {
            add(SvgDecoder.Factory())
        }
        .memoryCache {
            MemoryCache.Builder(this)
                .maxSizePercent(MemoryCacheSize)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(this.cacheDir.resolve("image_cache"))
                .maxSizePercent(DiskCacheSize)
                .build()
        }
        .respectCacheHeaders(false)
        .build()
}
