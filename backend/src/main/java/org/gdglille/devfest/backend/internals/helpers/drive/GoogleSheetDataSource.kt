package org.gdglille.devfest.backend.internals.helpers.drive

import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.Spreadsheet
import com.google.api.services.sheets.v4.model.SpreadsheetProperties

class GoogleSheetDataSource(private val service: Sheets) : SheetDataSource {
    override fun createFile(filename: String): String {
        var spreadsheet = Spreadsheet()
            .setProperties(SpreadsheetProperties().setTitle(filename))
        spreadsheet = service.spreadsheets().create(spreadsheet)
            .setFields("spreadsheetId")
            .execute()
        return spreadsheet.spreadsheetId
    }
}
