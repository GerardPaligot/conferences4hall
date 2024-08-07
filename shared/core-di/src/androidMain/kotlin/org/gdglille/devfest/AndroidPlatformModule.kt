package org.gdglille.devfest

import android.content.Context
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import org.gdglille.devfest.database.DatabaseWrapper
import org.gdglille.devfest.db.Conferences4HallDatabase
import org.gdglille.devfest.repositories.QrCodeGenerator
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.Locale

@OptIn(ExperimentalSettingsApi::class)
actual val platformModule = module {
    single<Conferences4HallDatabase> { DatabaseWrapper(androidContext()).createDb() }
    single<Platform> { Platform(AndroidContext(androidApplication())) }
    single<ObservableSettings> {
        AndroidSettings(
            androidContext().getSharedPreferences(
                /* name = */
                "org.gdglille.devfest.android",
                /* mode = */
                Context.MODE_PRIVATE
            )
        )
    }
    single<String>(named(AcceptLanguageNamed)) { Locale.getDefault().toLanguageTag() }
    single<QrCodeGenerator> { QrCodeGeneratorAndroid() }
}
