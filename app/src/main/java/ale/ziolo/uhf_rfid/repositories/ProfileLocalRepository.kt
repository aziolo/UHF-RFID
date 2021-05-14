package ale.ziolo.uhf_rfid.repositories

import ale.ziolo.uhf_rfid.model.AppDataBase
import ale.ziolo.uhf_rfid.model.daos.ProfileDao
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.util.Log

class ProfileLocalRepository(private val application: Application) {
    private var profileDao: ProfileDao
    private var con: Context

    init {
        val db: AppDataBase = AppDataBase.invoke(application.applicationContext)
        profileDao = db.ProfileDao()
        con = application.baseContext
    }


    fun getProfile(email: String): ProfileEntity {
        val dao = profileDao
        return GetProfileAsyncTask(
            dao,
            email
        ).execute().get()
    }

    fun insertProfile(profile: ProfileEntity) {
        InsertNewProfileAsyncTask(
            profileDao,
            con
        ).execute(profile)
    }

    fun updateProfile(profile: ProfileEntity) {
        val dao = profileDao
        UpdateProfileAsyncTask(
            dao,
            profile,
            con
        ).execute()
    }

    fun deleteProfile(profile: ProfileEntity) {
    }

    fun getOneProfile(): ProfileEntity {
        val dao = profileDao
        return GetOneProfileAsyncTask(
            dao
        ).execute().get()
    }

    private class GetProfileAsyncTask(val dao: ProfileDao, val email: String) :
        AsyncTask<String, Unit, ProfileEntity>() {
        override fun doInBackground(vararg params: String?): ProfileEntity {
            return dao.getProfile(email)
        }

    }

    private class GetOneProfileAsyncTask(val dao: ProfileDao) :
        AsyncTask<Unit, Unit, ProfileEntity>() {
        override fun doInBackground(vararg params: Unit?): ProfileEntity {
            return try {
                dao.getOneAndOnly()
            }catch (e: Exception){
                ProfileEntity()
            }

        }

    }

    private class InsertNewProfileAsyncTask(val dao: ProfileDao, val con: Context) :
        AsyncTask<ProfileEntity, Unit, Unit>() {
        override fun doInBackground(vararg params: ProfileEntity) {
            try {
                dao.insertAll(params[0])
                Log.i("INSERT", "operation insert profile successful.")
            } catch (e: RuntimeException) {
                Log.e("INSERT", "operation insert new profile failed")
            }
        }

    }


    private class UpdateProfileAsyncTask(
        val dao: ProfileDao,
        val profile: ProfileEntity,
        val con: Context
    ) : AsyncTask<Unit, Unit, Unit>() {
        var state = false
        override fun doInBackground(vararg params: Unit?) {
            try {
                state = true
                dao.updateProfile(profile)
                Log.i("UPDATE", "operation update profile successful")
            } catch (e: Exception) {
                state = false
                Log.e("UPDATE", "operation update profile failed")
            }
        }
    }

}