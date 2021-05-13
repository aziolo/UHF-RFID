package ale.ziolo.uhf_rfid.vieModels

import ale.ziolo.uhf_rfid.data.ProfileEntity
import ale.ziolo.uhf_rfid.data.ProfileLocalRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = ProfileLocalRepository(application)

    fun getProfile(email: String): ProfileEntity {
        return repository.getProfile(email)
    }

    fun getOneProfile(): ProfileEntity {
        return repository.getOneProfile()
    }

    fun insertProfile(profile: ProfileEntity) {
        repository.insertProfile(profile)
    }

    fun updateProfile(profile: ProfileEntity) {
        repository.updateProfile(profile)
    }
}