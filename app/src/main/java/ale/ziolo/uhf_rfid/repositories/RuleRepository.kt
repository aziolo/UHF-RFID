package ale.ziolo.uhf_rfid.repositories

import ale.ziolo.uhf_rfid.model.AppDataBase
import ale.ziolo.uhf_rfid.model.daos.RuleDao
import ale.ziolo.uhf_rfid.model.entities.RuleEntity
import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData

class RuleRepository(application: Application) {
    private var dao: RuleDao
    private var con: Context
    private var allRules: LiveData<List<RuleEntity>>
    init {
        val db: AppDataBase = AppDataBase.invoke(application.applicationContext)
        dao = db.RuleDao()
        con = application.baseContext
        allRules = dao.getAll()
    }
    fun getAllRules(): LiveData<List<RuleEntity>> {
        return allRules
    }

    fun insert(rule: RuleEntity) {
        InsertAsyncTask(
            dao
        ).execute(rule)
    }
    private class InsertAsyncTask(val dao: RuleDao) :
        AsyncTask<RuleEntity, Unit, Unit>() {
        override fun doInBackground(vararg params: RuleEntity) {
            try {
                dao.insertAll(params[0])
                Log.i("INSERT", "operation insert rule successful.")
            } catch (e: RuntimeException) {
                Log.e("INSERT", "operation insert new rule failed")
            }
        }
    }

    fun update(rule: RuleEntity){
        UpdateAsyncTask(
            dao,
            rule
        ).execute()
    }
    private class UpdateAsyncTask(
        val dao: RuleDao,
        val item: RuleEntity
    ) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            try {
                dao.updateRule(item)
                Log.i("UPDATE", "operation update rule successful")
            } catch (e: Exception) {
                Log.e("UPDATE", "operation update rule failed")
            }
        }
    }

}
