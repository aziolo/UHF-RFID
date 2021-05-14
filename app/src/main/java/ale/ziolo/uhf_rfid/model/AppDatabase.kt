package ale.ziolo.uhf_rfid.model
import ale.ziolo.uhf_rfid.model.daos.DeviceDAO
import ale.ziolo.uhf_rfid.model.daos.ProfileDAO
import ale.ziolo.uhf_rfid.model.entities.DeviceEntity
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.model.entities.ReadingEntity
import android.content.Context
import androidx.room.Database
import androidx.room.*
import androidx.room.RoomDatabase

@Database(
    entities = [ProfileEntity::class, DeviceEntity::class, ReadingEntity::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase(){
    abstract fun ProfileDAO(): ProfileDAO
    abstract fun DeviceDAO(): DeviceDAO

    companion object {

        //volatile guarantee that the value that is being read comes from the main memory not the cpu-cache, read the newest value from memory
        //operator ? = safe cals
        //return AppDataBase if not-null otherwise return null
        @Volatile
        private var instance: AppDataBase? = null

        //Any() returns true if has at least one element
        private val LOCK = Any()


        //synchronized with lock guarantee blocking the room. When we have one thread no one else(other thread) can use the room. reduce the mistakes
        //any thread that reaches the point lock the instance and does the work defined in the code-block:
        //Elvis operator ?:
        //if instance is not-null return instance otherwise return synchronized(LOCK)
        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {

                //if database does not exist build it
                instance
                    ?: buildDataBase(
                        context
                    ).also { instance = it }
            }

        //prawdziwa baza
        //private fun buildDataBase(context: Context) = Room.databaseBuilder(context, AppDataBase::class.java, "diabetic_daily.db").build()
        //baza do fazy testów, będzie działać tylko temporary
        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(context, AppDataBase::class.java, "local.db").build()
    }
}