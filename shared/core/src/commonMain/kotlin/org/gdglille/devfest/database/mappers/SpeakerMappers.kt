package org.gdglille.devfest.database.mappers

import kotlinx.collections.immutable.ImmutableList
import org.gdglille.devfest.db.SelectSpeakersByTalkId
import org.gdglille.devfest.models.Speaker
import org.gdglille.devfest.models.ui.SpeakerItemUi
import org.gdglille.devfest.models.ui.SpeakerUi
import org.gdglille.devfest.models.ui.TalkItemUi
import kotlin.reflect.KFunction2
import org.gdglille.devfest.db.Speaker as SpeakerDb

fun SpeakerDb.convertToSpeakerUi(
    getStringArg: KFunction2<String, List<String>, String>,
    talks: ImmutableList<TalkItemUi>
): SpeakerUi = SpeakerUi(
    name = display_name,
    pronouns = pronouns,
    bio = bio,
    jobTitle = job_title,
    company = company,
    activity = displayActivity(getStringArg),
    url = photo_url,
    twitterUrl = twitter,
    mastodonUrl = mastodon,
    githubUrl = github,
    linkedinUrl = linkedin,
    websiteUrl = website,
    talks = talks
)

fun SpeakerDb.convertToSpeakerItemUi(
    getStringArg: KFunction2<String, List<String>, String>
): SpeakerItemUi = SpeakerItemUi(
    id = id,
    name = display_name,
    pronouns = pronouns,
    company = displayActivity(getStringArg) ?: "",
    url = photo_url
)

fun SelectSpeakersByTalkId.convertSpeakerItemUi(
    getStringArg: KFunction2<String, List<String>, String>
) = SpeakerItemUi(
    id = id,
    name = display_name,
    pronouns = pronouns,
    company = displayActivity(getStringArg) ?: "",
    url = photo_url
)

fun SpeakerDb.displayActivity(getStringArg: KFunction2<String, List<String>, String>) = when {
    job_title != null && company != null -> getStringArg(
        "text_speaker_activity", listOf(job_title, company)
    )

    job_title == null && company != null -> company
    job_title != null && company == null -> job_title
    else -> null
}

fun SelectSpeakersByTalkId.displayActivity(
    getStringArg: KFunction2<String, List<String>, String>
) = when {
    job_title != null && company != null -> getStringArg(
        "text_speaker_activity", listOf(job_title, company)
    )

    job_title == null && company != null -> company
    job_title != null && company == null -> job_title
    else -> null
}

fun Speaker.convertToDb(eventId: String): SpeakerDb = SpeakerDb(
    id = id,
    display_name = displayName,
    pronouns = pronouns,
    bio = bio,
    job_title = jobTitle,
    company = company,
    photo_url = photoUrl,
    twitter = twitter,
    mastodon = mastodon,
    github = github,
    linkedin = linkedin,
    website = website,
    event_id = eventId
)
