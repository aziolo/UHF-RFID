package ale.ziolo.uhf_rfid.repositories

import ale.ziolo.uhf_rfid.model.AppDataBase
import ale.ziolo.uhf_rfid.model.daos.ItemDao
import ale.ziolo.uhf_rfid.model.entities.ItemEntity

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.util.Log

class ItemRepository(private val application: Application) {
    private var itemDao: ItemDao
    private var con: Context

    init {
        val db: AppDataBase = AppDataBase.invoke(application.applicationContext)
        itemDao = db.ItemDAO()
        con = application.baseContext
    }

    fun insert(item: ItemEntity) {
        InsertAsyncTask(
            itemDao,
            con
        ).execute(item)
    }

    private class InsertAsyncTask(val dao: ItemDao, val con: Context) :
        AsyncTask<ItemEntity, Unit, Unit>() {
        override fun doInBackground(vararg params: ItemEntity) {
            try {
                dao.insertAll(params[0])
                Log.i("INSERT", "operation insert device successful.")
            } catch (e: RuntimeException) {
                Log.e("INSERT", "operation insert new device failed")
            }
        }

    }
}