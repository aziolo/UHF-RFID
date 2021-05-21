package ale.ziolo.uhf_rfid.view.ui.itemsFragment

import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.repositories.ItemRepository
import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ItemsViewModel(@NonNull application: Application?) : AndroidViewModel(application!!) {

    private val repository: ItemRepository = ItemRepository(application!!)
    private val allData: LiveData<List<ItemEntity>> = repository.getAllItems()

    fun getAllData(): LiveData<List<ItemEntity>> {
        return allData
    }
    fun updateStatus(item: ItemEntity){
        repository.update(item)
    }

}
