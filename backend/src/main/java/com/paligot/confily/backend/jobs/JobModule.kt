package com.paligot.confily.backend.jobs

import com.paligot.confily.backend.events.EventModule.eventDao
import com.paligot.confily.backend.internals.InternalModule.database
import com.paligot.confily.backend.partners.PartnerModule.partnerDao
import com.paligot.confily.backend.third.parties.welovedevs.WeLoveDevsModule.wldApi

object JobModule {
    val jobDao = lazy { JobDao(database.value) }
    val jobRepository =
        lazy { JobRepository(wldApi.value, eventDao.value, partnerDao.value, jobDao.value) }
}