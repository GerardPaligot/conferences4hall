package org.gdglille.devfest.benchmark

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalBaselineProfilesApi
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {
    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() = rule.collectBaselineProfile("org.gdglille.devfest.android") {
        startApplicationAgenda()
    }
}

fun MacrobenchmarkScope.startApplicationAgenda() {
    pressHome()
    startActivityAndWait()
    // val contentList = device.findObject(By.res("schedule_list"))
    // Wait until a snack collection item within the list is rendered
    // contentList.wait(Until.hasObject(By.res("schedule_item 09:00")), 5_000)
}
