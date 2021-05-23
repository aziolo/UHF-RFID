package ale.ziolo.uhf_rfid.view.ui.synchronize

import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.model.entities.RuleEntity
import ale.ziolo.uhf_rfid.repositories.ItemRepository
import ale.ziolo.uhf_rfid.repositories.ProfileRepository
import ale.ziolo.uhf_rfid.repositories.RuleRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class SynchronizeViewModel(application: Application) : AndroidViewModel(application) {

    private var profileRepo = ProfileRepository(application)
    private var itemRepo = ItemRepository(application)
    private var ruleRepo = RuleRepository(application)

    fun insertProfile(profile: ProfileEntity, mode: String) {
        profileRepo.insertProfile(profile, mode)
    }
    fun insertItem(item: ItemEntity){
        itemRepo.insert(item)
    }
    fun insertRule(rule: RuleEntity){
        ruleRepo.insert(rule)
    }

}