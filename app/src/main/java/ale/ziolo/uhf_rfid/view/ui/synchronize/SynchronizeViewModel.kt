package ale.ziolo.uhf_rfid.view.ui.synchronize

import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.model.entities.RuleEntity
import ale.ziolo.uhf_rfid.repositories.ProfileRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class SynchronizeViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = ProfileRepository(application)

    fun insertProfile(profile: ProfileEntity) {
        repository.insertProfile(profile)
    }
    fun insertItem(item: ItemEntity){

    }
    fun insertRule(rule: RuleEntity){}

}