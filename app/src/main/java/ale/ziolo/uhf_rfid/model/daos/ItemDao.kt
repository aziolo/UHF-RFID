package ale.ziolo.uhf_rfid.model.daos


import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): LiveData<List<ItemEntity>>

    @Query("SELECT * FROM item")
    fun getOneAndOnly(): ItemEntity

    @Insert
    fun insertAll(vararg item: ItemEntity)

    @Delete
    fun delete(item: ItemEntity)

    @Update
    fun updateItem(vararg itemUpd: ItemEntity)

}
