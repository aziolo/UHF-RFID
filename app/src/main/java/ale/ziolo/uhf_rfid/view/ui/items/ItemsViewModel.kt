package ale.ziolo.uhf_rfid.view.ui.items

import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.repositories.ItemRepository
import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


//class ItemsViewModel(@NonNull application: Application?) : ViewModel() {
//
//    private val repository: ItemRepository = ItemRepository(application!!)
//    private val allData: LiveData<List<ItemEntity>> = repository.getAllItems()
//
//    fun getAllData(): LiveData<List<ItemEntity>> {
//        return allData
//    }
//
//}

public class ItemsViewModel(@NonNull application: Application?) : AndroidViewModel(application!!) {

    private val repository: ItemRepository = ItemRepository(application!!)
    private val allData: LiveData<List<ItemEntity>> = repository.getAllItems()

    fun getAllData(): LiveData<List<ItemEntity>> {
        return allData
    }
    fun updateStatus(item: ItemEntity){
        repository.update(item)
    }

}

//class ItemsViewModelFactory(private val repository: ItemRepository)
//    : ViewModelProvider.NewInstanceFactory() {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return ItemsViewModel(repository) as T
//    }
