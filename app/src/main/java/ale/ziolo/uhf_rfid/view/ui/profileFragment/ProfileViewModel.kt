package ale.ziolo.uhf_rfid.view.ui.profileFragment

import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.repositories.ProfileRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = ProfileRepository(application)

    fun getProfile(): ProfileEntity {
        return repository.getOneProfile()
    }

}