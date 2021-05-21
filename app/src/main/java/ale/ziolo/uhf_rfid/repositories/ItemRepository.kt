package ale.ziolo.uhf_rfid.repositories

import ale.ziolo.uhf_rfid.model.AppDataBase
import ale.ziolo.uhf_rfid.model.daos.ItemDao
import ale.ziolo.uhf_rfid.model.daos.ProfileDao
import ale.ziolo.uhf_rfid.model.entities.ItemEntity

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData

class ItemRepository(application: Application) {
    private var dao: ItemDao
    private var con: Context
    private var allItems: LiveData<List<ItemEntity>>

    init {
        val db: AppDataBase = AppDataBase.invoke(application.applicationContext)
        dao = db.ItemDao()
        con = application.baseContext
        allItems = dao.getAll()
    }
    fun getAllItems(): LiveData<List<ItemEntity>>{
        return allItems
    }

    fun insert(item: ItemEntity) {
        InsertAsyncTask(
            dao
        ).execute(item)
    }
    private class InsertAsyncTask(val dao: ItemDao) :
        AsyncTask<ItemEntity, Unit, Unit>() {
        override fun doInBackground(vararg params: ItemEntity) {
            try {
                dao.insertAll(params[0])
                Log.i("INSERT", "operation insert item successful.")
            } catch (e: RuntimeException) {
                Log.e("INSERT", "operation insert new item failed")
            }
        }

    }

    fun update(item: ItemEntity){
        UpdateItemAsyncTask(
            dao,
            item
        ).execute()
    }

    private class UpdateItemAsyncTask(
        val dao: ItemDao,
        val item: ItemEntity
    ) : AsyncTask<Unit, Unit, Unit>() {
        var state = false
        override fun doInBackground(vararg params: Unit?) {
            try {
                state = true
                dao.updateItem(item)
                Log.i("UPDATE", "operation update item successful")
            } catch (e: Exception) {
                state = false
                Log.e("UPDATE", "operation update item failed")
            }
        }
    }

    fun getList(): List<ItemEntity> {
        val dao = dao
        return GetListAsyncTask(
            dao
        ).execute().get()
    }
    private class GetListAsyncTask(val dao: ItemDao) :
        AsyncTask<String, Unit, List<ItemEntity>>() {
        override fun doInBackground(vararg params: String?): List<ItemEntity> {
            return dao.getList()
        }
    }
}