package org.gdglille.devfest.backend.partners

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.gdglille.devfest.backend.internals.helpers.database.Database
import org.gdglille.devfest.backend.internals.helpers.database.get
import org.gdglille.devfest.backend.internals.helpers.database.getAll
import org.gdglille.devfest.backend.internals.helpers.image.Png
import org.gdglille.devfest.backend.internals.helpers.storage.Storage
import org.gdglille.devfest.backend.internals.helpers.storage.Upload

private const val CollectionName = "companies"

class PartnerDao(private val database: Database, private val storage: Storage) {
    suspend fun getAll(eventId: String): List<PartnerDb> = database
        .getAll<PartnerDb>(eventId = eventId, collectionName = CollectionName)
        .map {
            if (it.siteUrl.contains(Regex("^http[s]{0,1}://"))) return@map it
            return@map it.copy(siteUrl = "https://${it.siteUrl}")
        }

    suspend fun createOrUpdate(eventId: String, partner: PartnerDb): String = coroutineScope {
        if (partner.id == "") return@coroutineScope database.insert(
            eventId = eventId,
            collectionName = CollectionName
        ) { partner.copy(id = it) }
        val existing = database.get<PartnerDb>(eventId = eventId, collectionName = CollectionName, id = partner.id)
        if (existing == null) database.insert(
            eventId = eventId, collectionName = CollectionName,
            id = partner.id,
            item = partner
        )
        else database.update(
            eventId = eventId,
            collectionName = CollectionName,
            id = partner.id,
            item = partner
        )
        return@coroutineScope partner.id
    }

    suspend fun uploadPartnerLogos(eventId: String, partnerId: String, pngs: List<Png>): List<Upload> = coroutineScope {
        return@coroutineScope pngs
            .filter { it.content != null }
            .map { png ->
                async {
                    storage.upload(
                        filename = "$eventId/partners/$partnerId/${png.size}.png",
                        content = png.content!!
                    )
                }
            }
            .awaitAll()
    }

    suspend fun hasPartners(eventId: String): Boolean =
        database.count(eventId = eventId, collectionName = CollectionName) > 0
}
