package ale.ziolo.uhf_rfid.model.daos

import ale.ziolo.uhf_rfid.model.entities.DeviceEntity
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DeviceDAO {
    @Query("SELECT * FROM device")
    fun getAll(): LiveData<List<DeviceEntity>>

    @Query("SELECT * FROM device")
    fun getOneAndOnly(): DeviceEntity

    @Insert
    fun insertAll(vararg device: DeviceEntity)

    @Delete
    fun delete(profile: DeviceEntity)

}