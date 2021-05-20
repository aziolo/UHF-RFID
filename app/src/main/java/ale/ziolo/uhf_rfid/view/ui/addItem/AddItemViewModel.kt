package ale.ziolo.uhf_rfid.view.ui.addItem

import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.repositories.ItemRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AddItemViewModel(application: Application) : AndroidViewModel(application) {
    private var repository = ItemRepository(application)

    fun insert(item: ItemEntity) {
        repository.insert(item)
    }
}