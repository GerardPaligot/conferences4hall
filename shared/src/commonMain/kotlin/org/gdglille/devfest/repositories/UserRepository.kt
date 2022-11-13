package org.gdglille.devfest.repositories

import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.gdglille.devfest.Image
import org.gdglille.devfest.database.UserDao
import org.gdglille.devfest.models.UserNetworkingUi
import org.gdglille.devfest.models.UserProfileUi
import org.gdglille.devfest.vcard.encodeToString

interface UserRepository {
    fun fetchProfile(): Flow<UserProfileUi?>
    fun saveProfile(email: String, firstName: String, lastName: String, company: String)
    fun fetchNetworking(): Flow<List<UserNetworkingUi>>
    fun insertNetworkingProfile(user: UserNetworkingUi): Boolean
    fun deleteNetworkProfile(email: String)

    object Factory {
        fun create(userDao: UserDao, qrCodeGenerator: QrCodeGenerator): UserRepository =
            UserRepositoryImpl(userDao, qrCodeGenerator)
    }
}

interface QrCodeGenerator {
    fun generate(text: String): Image
}

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val qrCodeGenerator: QrCodeGenerator
) : UserRepository {
    @NativeCoroutineScope
    private val coroutineScope: CoroutineScope = MainScope()

    override fun fetchProfile(): Flow<UserProfileUi?> = combine(
        userDao.fetchProfile(),
        userDao.fetchUserPreview(),
        transform = { profile, preview ->
            return@combine profile ?: preview
        }
    )

    override fun saveProfile(email: String, firstName: String, lastName: String, company: String) {
        val qrCode = qrCodeGenerator.generate(UserNetworkingUi(email, firstName, lastName, company).encodeToString())
        val profile = UserProfileUi(email, firstName, lastName, company, qrCode)
        userDao.insertUser(profile)
    }

    override fun fetchNetworking(): Flow<List<UserNetworkingUi>> = userDao.fetchNetworking()
    override fun insertNetworkingProfile(user: UserNetworkingUi): Boolean {
        val hasRequiredFields = user.email != "" && user.lastName != "" && user.firstName != ""
        if (!hasRequiredFields) return false
        userDao.insertEmailNetworking(user)
        return true
    }

    override fun deleteNetworkProfile(email: String) = userDao.deleteNetworking(email)
}
