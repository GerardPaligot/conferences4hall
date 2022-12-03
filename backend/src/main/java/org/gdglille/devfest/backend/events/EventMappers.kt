@file:Suppress("TooManyFunctions")

package org.gdglille.devfest.backend.events

import org.gdglille.devfest.backend.internals.slug
import org.gdglille.devfest.backend.network.conferencehall.Event
import org.gdglille.devfest.backend.partners.convertToModel
import org.gdglille.devfest.models.Address
import org.gdglille.devfest.models.EventLunchMenu
import org.gdglille.devfest.models.EventPartners
import org.gdglille.devfest.models.EventV2
import org.gdglille.devfest.models.FeaturesActivated
import org.gdglille.devfest.models.QuestionAndResponse
import org.gdglille.devfest.models.QuestionAndResponseAction
import org.gdglille.devfest.models.inputs.BilletWebConfigInput
import org.gdglille.devfest.models.inputs.CategoryInput
import org.gdglille.devfest.models.inputs.EventInput
import org.gdglille.devfest.models.inputs.LunchMenuInput
import org.gdglille.devfest.models.inputs.QuestionAndResponseActionInput
import org.gdglille.devfest.models.inputs.QuestionAndResponseInput

fun Event.convertToDb(year: String, eventId: String, apiKey: String) = EventDb(
    slugId = this.name.slug(),
    year = year,
    conferenceHallId = eventId,
    apiKey = apiKey,
    name = this.name,
    address = AddressDb(
        formatted = this.address.formattedAddress.split(",").map { it.trim() },
        address = this.address.formattedAddress,
        country = this.address.country.longName,
        countryCode = this.address.country.shortName,
        city = this.address.locality.longName,
        lat = this.address.latLng.lat,
        lng = this.address.latLng.lng
    ),
    startDate = this.conferenceDates.start,
    endDate = this.conferenceDates.end
)

fun QuestionAndResponseActionDb.convertToModel() = QuestionAndResponseAction(
    order = order,
    label = label,
    url = url
)

fun QuestionAndResponseDb.convertToModel() = QuestionAndResponse(
    order = order,
    question = question,
    response = response,
    actions = this.actions.map { it.convertToModel() }
)

fun LunchMenuDb.convertToModel() = EventLunchMenu(
    name = name,
    dish = dish,
    accompaniment = accompaniment,
    dessert = dessert
)

fun AddressDb.convertToModel() = Address(
    formatted = this.formatted,
    address = this.address,
    country = this.country,
    countryCode = this.countryCode,
    city = this.city,
    lat = this.lat,
    lng = this.lng
)

fun EventDb.convertToFeaturesActivatedModel(hasPartnerList: Boolean) = FeaturesActivated(
    hasNetworking = features.hasNetworking,
    hasSpeakerList = !features.hasNetworking,
    hasPartnerList = hasPartnerList,
    hasMenus = menus.isNotEmpty(),
    hasQAndA = qanda.isNotEmpty(),
    hasBilletWebTicket = billetWebConfig != null
)

fun EventDb.convertToModel(partners: EventPartners) = org.gdglille.devfest.models.Event(
    id = this.slugId,
    name = this.name,
    address = this.address.convertToModel(),
    startDate = this.startDate,
    endDate = this.endDate,
    partners = partners,
    menus = menus.map { it.convertToModel() },
    qanda = qanda.map { it.convertToModel() },
    coc = coc,
    features = this.convertToFeaturesActivatedModel(
        partners.golds.isNotEmpty() ||
            partners.silvers.isNotEmpty() ||
            partners.bronzes.isNotEmpty() ||
            partners.others.isNotEmpty()
    ),
    twitterUrl = this.twitterUrl,
    linkedinUrl = this.linkedinUrl,
    faqLink = this.faqLink,
    codeOfConductLink = this.codeOfConductLink,
    updatedAt = this.updatedAt
)

fun EventDb.convertToModelV2(hasPartnerList: Boolean) = EventV2(
    id = this.slugId,
    name = this.name,
    address = this.address.convertToModel(),
    startDate = this.startDate,
    endDate = this.endDate,
    menus = menus.map { it.convertToModel() },
    qanda = qanda.map { it.convertToModel() },
    coc = coc,
    features = this.convertToFeaturesActivatedModel(hasPartnerList),
    twitterUrl = this.twitterUrl,
    linkedinUrl = this.linkedinUrl,
    faqLink = this.faqLink,
    codeOfConductLink = this.codeOfConductLink,
    updatedAt = this.updatedAt
)

fun QuestionAndResponseActionInput.convertToDb(order: Int) = QuestionAndResponseActionDb(
    order = order,
    label = label,
    url = url
)

fun QuestionAndResponseInput.convertToDb(order: Int) = QuestionAndResponseDb(
    order = order,
    question = question,
    response = response,
    actions = actions.mapIndexed { index, it -> it.convertToDb(index) }
)

fun LunchMenuInput.convertToDb() = LunchMenuDb(
    name = name,
    dish = dish,
    accompaniment = accompaniment,
    dessert = dessert
)

fun CategoryInput.convertToDb() = CategoryDb(
    name = name,
    color = color,
    icon = icon
)

fun BilletWebConfigInput.convertToDb() = BilletWebConfigurationDb(
    eventId = eventId,
    userId = userId,
    apiKey = apiKey
)

fun EventInput.convertToDb(event: EventDb, openFeedbackId: String?, apiKey: String) = EventDb(
    slugId = event.name.slug(),
    year = event.year,
    conferenceHallId = event.conferenceHallId,
    openFeedbackId = openFeedbackId ?: event.openFeedbackId,
    billetWebConfig = this.billetWebConfig?.convertToDb(),
    apiKey = apiKey,
    name = this.name,
    address = AddressDb(
        formatted = this.address.address.split(",").map { it.trim() },
        address = this.address.address,
        country = this.address.country,
        countryCode = this.address.countryCode,
        city = this.address.city,
        lat = this.address.lat,
        lng = this.address.lng
    ),
    menus = event.menus,
    qanda = event.qanda,
    coc = event.coc,
    startDate = this.startDate,
    endDate = this.endDate,
    sponsoringTypes = this.sponsoringTypes,
    formats = this.formats,
    twitterUrl = this.twitterUrl,
    linkedinUrl = this.linkedinUrl,
    faqLink = this.faqLink,
    codeOfConductLink = this.codeOfConductLink,
    updatedAt = this.updatedAt
)
