package org.gdglille.devfest.backend.internals.helpers.drive

import com.google.api.services.drive.Drive

interface DriveDataSource {
    fun list(): List<String>
    fun grantPermission(fileId: String, email: String)

    object Factory {
        fun create(service: Drive): DriveDataSource = GoogleDriveDataSource(service)
    }
}
