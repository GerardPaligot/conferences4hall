package org.gdglille.devfest.database.mappers

import kotlinx.collections.immutable.persistentListOf
import org.gdglille.devfest.models.Speaker
import org.gdglille.devfest.models.ui.SpeakerItemUi
import org.gdglille.devfest.models.ui.SpeakerUi
import org.gdglille.devfest.db.Speaker as SpeakerDb

object SpeakerMappers {
    val speakerUi =
        { _: String, display_name: String, pronouns: String?, bio: String, job_title: String?,
            company: String?, photo_url: String, twitter: String?, mastodon: String?,
            github: String?, linkedin: String?, website: String?, _: String ->
            SpeakerUi(
                name = display_name,
                pronouns = pronouns,
                bio = bio,
                jobTitle = job_title,
                company = company,
                url = photo_url,
                twitterUrl = twitter,
                mastodonUrl = mastodon,
                githubUrl = github,
                linkedinUrl = linkedin,
                websiteUrl = website,
                talks = persistentListOf()
            )
        }

    val speakerItemUi =
        { id: String, display_name: String, pronouns: String?, _: String, job_title: String?,
            company: String?, photo_url: String, _: String?, _: String?, _: String?, _: String?,
            _: String?, _: String ->
            SpeakerItemUi(
                id = id,
                name = display_name,
                pronouns = pronouns,
                company = displayActivity(job_title, company),
                url = photo_url
            )
        }

    fun displayActivity(jobTitle: String?, company: String?) = when {
        jobTitle != null && company != null -> "$jobTitle - $company"
        jobTitle == null && company != null -> company
        jobTitle != null && company == null -> jobTitle
        else -> ""
    }
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
