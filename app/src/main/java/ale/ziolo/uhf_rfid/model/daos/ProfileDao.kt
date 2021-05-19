package ale.ziolo.uhf_rfid.model.daos

import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.concurrent.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile")
    fun getAll(): LiveData<List<ProfileEntity>>

    @Query("SELECT * FROM profile WHERE email LIKE :uid")
    fun getProfile(uid: String): ProfileEntity

    @Query("SELECT * FROM profile")
    fun getOneAndOnly(): ProfileEntity

    @Insert
    fun insertAll(vararg profile: ProfileEntity)

    @Delete
    fun delete(profile: ProfileEntity)

    @Update
    fun updateProfile(vararg profileUpd: ProfileEntity)

    //vararg we can put more than one value

    @Query("SELECT * FROM profile")
    fun getProfiles(): LiveData<List<ProfileEntity>>
}