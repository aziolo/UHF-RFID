package ale.ziolo.uhf_rfid.view.ui.profile

import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.repositories.ProfileLocalRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = ProfileLocalRepository(application)

    fun getProfile(): ProfileEntity {
        return repository.getOneProfile()
    }

}