package org.gdglille.devfest.android.core.test

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class TestJUnitRunner : AndroidJUnitRunner() {
    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(classLoader, TestApplication::class.java.name, context)
    }
}
