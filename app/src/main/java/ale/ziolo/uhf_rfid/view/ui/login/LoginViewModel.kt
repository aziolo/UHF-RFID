package ale.ziolo.uhf_rfid.view.ui.login

import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.repositories.ProfileRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = ProfileRepository(application)

    fun insertProfile(profile: ProfileEntity) {
        repository.insertProfile(profile)
    }
}