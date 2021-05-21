package ale.ziolo.uhf_rfid.view.ui.addRule

import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.model.entities.RuleEntity
import ale.ziolo.uhf_rfid.repositories.ItemRepository
import ale.ziolo.uhf_rfid.repositories.RuleRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AddRuleViewModel(application: Application) : AndroidViewModel(application) {
    private val itemRepo: ItemRepository = ItemRepository(application)
    private val ruleRepo: RuleRepository = RuleRepository(application)

    fun getList(): List<ItemEntity> {
        return itemRepo.getList()
    }
    fun update(rule: RuleEntity){
        ruleRepo.update(rule)
    }

    fun insert(rule: RuleEntity) {
        ruleRepo.insert(rule)
    }
}
