package org.gdglille.devfest.database

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.map
import org.gdglille.devfest.Image
import org.gdglille.devfest.db.Conferences4HallDatabase
import org.gdglille.devfest.models.Attendee
import org.gdglille.devfest.models.CoCUi
import org.gdglille.devfest.models.EventInfoUi
import org.gdglille.devfest.models.EventItemList
import org.gdglille.devfest.models.EventItemListUi
import org.gdglille.devfest.models.EventItemUi
import org.gdglille.devfest.models.EventUi
import org.gdglille.devfest.models.EventV2
import org.gdglille.devfest.models.MenuItemUi
import org.gdglille.devfest.models.QuestionAndResponseActionUi
import org.gdglille.devfest.models.QuestionAndResponseUi
import org.gdglille.devfest.models.TicketInfoUi
import org.gdglille.devfest.models.TicketUi
import org.gdglille.devfest.toByteArray
import org.gdglille.devfest.toNativeImage

class EventDao(private val db: Conferences4HallDatabase, private val eventId: String) {
    private val eventMapper =
        { _: String, name: String, formattedAddress: List<String>, address: String, latitude: Double, longitude: Double,
            date: String, _: String, _: String, _: String?, twitter: String?, twitterUrl: String?,
            linkedin: String?, linkedinUrl: String?, faqUrl: String, cocUrl: String, _: Long ->
            EventInfoUi(
                name = name,
                formattedAddress = formattedAddress,
                address = address,
                latitude = latitude,
                longitude = longitude,
                date = date,
                twitter = twitter,
                twitterUrl = twitterUrl,
                linkedin = linkedin,
                linkedinUrl = linkedinUrl,
                faqLink = faqUrl,
                codeOfConductLink = cocUrl
            )
        }

    private val eventItemMapper = { id: String, name: String, date: String, _: Long, _: Boolean ->
        EventItemUi(id = id, name = name, date = date)
    }

    private val menuMapper = { name: String, dish: String, accompaniment: String, dessert: String ->
        MenuItemUi(name = name, dish = dish, accompaniment = accompaniment, dessert = dessert)
    }

    private val cocMapper = { coc: String, email: String, phone: String? ->
        CoCUi(text = coc, phone = phone, email = email)
    }

    private val ticketMapper =
        { _: String, ext_id: String?, _: String?, _: String?, firstname: String?, lastname: String?, _: String, qrcode: ByteArray ->
            TicketUi(
                info = if (ext_id != null && firstname != null && lastname != null) {
                    TicketInfoUi(
                        id = ext_id,
                        firstName = firstname,
                        lastName = lastname
                    )
                } else null,
                qrCode = qrcode.toNativeImage()
            )
        }

    fun fetchEvent(): Flow<EventUi> = db.transactionWithResult {
        return@transactionWithResult db.eventQueries.selectEvent(eventId, eventMapper).asFlow()
            .combineTransform(
                db.ticketQueries.selectTicket(eventId, ticketMapper).asFlow()
            ) { event, ticket ->
                val eventInfo = event.executeAsOneOrNull() ?: return@combineTransform
                emit(EventUi(eventInfo = eventInfo, ticket = ticket.executeAsOneOrNull()))
            }
    }

    fun fetchFutureEvent(): Flow<EventItemListUi> =
        db.eventQueries.selectEventItem(eventItemMapper).asFlow().mapToList().map {
            EventItemListUi(future = it)
        }

    fun fetchQAndA(): Flow<List<QuestionAndResponseUi>> = db.transactionWithResult {
        return@transactionWithResult combine(
            db.qAndAQueries.selectQAndA().asFlow().mapToList(),
            db.qAndAQueries.selectQAndAActions(eventId).asFlow().mapToList(),
            transform = { qAndADb, actionsDb ->
                qAndADb.map { qanda ->
                    QuestionAndResponseUi(
                        question = qanda.question,
                        response = qanda.response,
                        actions = actionsDb
                            .filter { it.qanda_id == qanda.order_ }
                            .sortedBy { it.order_ }
                            .map { QuestionAndResponseActionUi(label = it.label, url = it.url) }
                    )
                }
            }
        )
    }

    fun fetchMenus(): Flow<List<MenuItemUi>> =
        db.menuQueries.selectMenus(menuMapper).asFlow().mapToList()

    fun fetchCoC(): Flow<CoCUi> = db.eventQueries.selectCoc(eventId, cocMapper).asFlow().mapToOne()

    fun insertEvent(event: EventV2) = db.transaction {
        val eventDb = event.convertToModelDb()
        db.eventQueries.insertEvent(
            id = eventDb.id,
            name = eventDb.name,
            formatted_address = eventDb.formatted_address,
            address = eventDb.address,
            latitude = eventDb.latitude,
            longitude = eventDb.longitude,
            date = eventDb.date,
            coc = eventDb.coc,
            contact_email = eventDb.contact_email,
            contact_phone = eventDb.contact_phone,
            twitter = eventDb.twitter,
            twitter_url = eventDb.twitter_url,
            linkedin = eventDb.linkedin,
            linkedin_url = eventDb.linkedin_url,
            faq_url = eventDb.faq_url,
            coc_url = eventDb.coc_url,
            updated_at = eventDb.updated_at
        )
        event.qanda.forEach { qAndA ->
            db.qAndAQueries.insertQAndA(qAndA.order.toLong(), eventId, qAndA.question, qAndA.response)
            qAndA.actions.forEach {
                db.qAndAQueries.insertQAndAAction(it.order.toLong(), eventId, qAndA.order.toLong(), it.label, it.url)
            }
        }
        event.menus.forEach {
            db.menuQueries.insertMenu(it.name, it.dish, it.accompaniment, it.dessert)
        }
        db.featuresActivatedQueries.insertFeatures(
            event_id = eventId,
            has_networking = event.features.hasNetworking,
            has_speaker_list = event.features.hasSpeakerList,
            has_partner_list = event.features.hasPartnerList,
            has_menus = event.features.hasMenus,
            has_qanda = event.features.hasQAndA,
            has_billet_web_ticket = event.features.hasBilletWebTicket
        )
    }

    fun insertEventItems(items: List<EventItemList>) = db.transaction {
        items.forEach {
            val itemDb = it.convertToModelDb()
            db.eventQueries.insertEventItem(
                id = itemDb.id,
                name = itemDb.name,
                date = itemDb.date,
                timestamp = itemDb.timestamp,
                past = itemDb.past
            )
        }
    }

    fun updateTicket(qrCode: Image, barcode: String, attendee: Attendee?) =
        db.ticketQueries.insertUser(
            id = attendee?.id,
            ext_id = attendee?.idExt,
            event_id = eventId,
            email = attendee?.email,
            firstname = attendee?.firstname,
            lastname = attendee?.name,
            barcode = barcode,
            qrcode = qrCode.toByteArray()
        )
}
