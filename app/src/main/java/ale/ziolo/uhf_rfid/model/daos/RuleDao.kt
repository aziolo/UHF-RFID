package ale.ziolo.uhf_rfid.model.daos

import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.model.entities.RuleEntity
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RuleDao {
    @Query("SELECT * FROM rule")
    fun getAll(): LiveData<List<RuleEntity>>

    @Query("SELECT * FROM rule")
    fun getOneAndOnly(): RuleEntity

    @Insert
    fun insertAll(vararg rule: RuleEntity)

    @Delete
    fun delete(rule: RuleEntity)

    @Update
    fun updateRule(vararg itemUpd: RuleEntity)

}