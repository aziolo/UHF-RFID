package ale.ziolo.uhf_rfid.repositories

import ale.ziolo.uhf_rfid.model.AppDataBase
import ale.ziolo.uhf_rfid.model.daos.DeviceDAO
import ale.ziolo.uhf_rfid.model.daos.ProfileDAO
import ale.ziolo.uhf_rfid.model.entities.DeviceEntity
import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.util.Log

class DeviceRepository(private val application: Application) {
    private var deviceDao: DeviceDAO
    private var con: Context

    init {
        val db: AppDataBase = AppDataBase.invoke(application.applicationContext)
        deviceDao = db.DeviceDAO()
        con = application.baseContext
    }

    fun insert(device: DeviceEntity) {
        InsertAsyncTask(
            deviceDao,
            con
        ).execute(device)
    }

    private class InsertAsyncTask(val dao: DeviceDAO, val con: Context) :
        AsyncTask<DeviceEntity, Unit, Unit>() {
        override fun doInBackground(vararg params: DeviceEntity) {
            try {
                dao.insertAll(params[0])
                Log.i("INSERT", "operation insert device successful.")
            } catch (e: RuntimeException) {
                Log.e("INSERT", "operation insert new device failed")
            }
        }
    }
}