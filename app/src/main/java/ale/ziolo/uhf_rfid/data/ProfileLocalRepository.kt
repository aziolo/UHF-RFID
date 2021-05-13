package ale.ziolo.uhf_rfid.data

import ale.ziolo.uhf_rfid.MainActivity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import java.util.concurrent.Flow

class ProfileLocalRepository(private val application: Application) {
    private var profileDao: ProfileDAO
    private var con: Context

    init {
        val db: AppDataBase = AppDataBase.invoke(application.applicationContext)
        profileDao = db.ProfileDAO()
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

    private class GetProfileAsyncTask(val dao: ProfileDAO, val email: String) :
        AsyncTask<String, Unit, ProfileEntity>() {
        override fun doInBackground(vararg params: String?): ProfileEntity {
            return dao.getProfile(email)
        }

    }

    private class GetOneProfileAsyncTask(val dao: ProfileDAO) :
        AsyncTask<Unit, Unit, ProfileEntity>() {
        override fun doInBackground(vararg params: Unit?): ProfileEntity {
            return try {
                dao.getOneAndOnly()
            }catch (e: Exception){
                ProfileEntity()
            }

        }

    }

    private class InsertNewProfileAsyncTask(val dao: ProfileDAO, val con: Context) :
        AsyncTask<ProfileEntity, Unit, Unit>() {
        override fun doInBackground(vararg params: ProfileEntity) {
            try {
                dao.insertAll(params[0])
                Log.i("INSERT", "operation insert profile successful.")
            } catch (e: RuntimeException) {
                Log.e("INSERT", "operation insert new profile failed")
            }
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            val intent = Intent(con, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            con.startActivity(intent)
        }
    }


    private class UpdateProfileAsyncTask(
        val dao: ProfileDAO,
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