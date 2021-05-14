package ale.ziolo.uhf_rfid.viewModels

import ale.ziolo.uhf_rfid.model.entities.DeviceEntity
import ale.ziolo.uhf_rfid.repositories.DeviceRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class DeviceViewModel (application: Application) : AndroidViewModel(application) {

    private var repository = DeviceRepository(application)

    fun insert(device: DeviceEntity) {
        repository.insert(device)
    }

}