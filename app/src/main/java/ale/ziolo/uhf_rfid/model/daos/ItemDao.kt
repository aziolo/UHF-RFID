package ale.ziolo.uhf_rfid.model.daos


import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

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
}
