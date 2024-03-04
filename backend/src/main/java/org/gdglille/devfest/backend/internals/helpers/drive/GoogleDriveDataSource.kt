package org.gdglille.devfest.backend.internals.helpers.drive

import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.Permission

private const val PageSize = 10

class GoogleDriveDataSource(private val service: Drive) : DriveDataSource {
    override fun list(): List<String> {
        val result = service.files().list()
            .setPageSize(PageSize)
            .setFields("nextPageToken, files(id, name)")
            .execute()
        val files = result.files
        service.permissions()
        return files.map { it.name }
    }

    override fun grantPermission(fileId: String, email: String) {
        val userPermission: Permission = Permission()
            .setType("user")
            .setRole("writer")
        userPermission.setEmailAddress(email)
        service.permissions().create(fileId, userPermission).setFields("id").execute()
    }
}
