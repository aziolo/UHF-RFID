package ale.ziolo.uhf_rfid.view.ui.ruleFragment


import ale.ziolo.uhf_rfid.model.entities.RuleEntity
import ale.ziolo.uhf_rfid.repositories.RuleRepository
import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RulesViewModel(@NonNull application: Application?) : AndroidViewModel(application!!) {

    private val repository: RuleRepository = RuleRepository(application!!)
    private val allData: LiveData<List<RuleEntity>> = repository.getAllRules()

    fun getAllData(): LiveData<List<RuleEntity>> {
        return allData
    }
    fun updateStatus(item: RuleEntity){
        repository.update(item)
    }
    fun delete(rule: RuleEntity){
        repository.delete(rule)
    }

}