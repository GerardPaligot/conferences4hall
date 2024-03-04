package org.gdglille.devfest.backend.internals.helpers.drive

import com.google.api.services.sheets.v4.Sheets

interface SheetDataSource {
    fun createFile(filename: String): String

    object Factory {
        fun create(service: Sheets): SheetDataSource = GoogleSheetDataSource(service)
    }
}
