package ale.ziolo.uhf_rfid.viewModels

import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.repositories.ProfileLocalRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class FullProfileViewModel(application: Application) : AndroidViewModel(application) {

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