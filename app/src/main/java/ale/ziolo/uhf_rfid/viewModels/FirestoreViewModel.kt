package ale.ziolo.uhf_rfid.viewModels

import ale.ziolo.uhf_rfid.repositories.FirestoreRepository
import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FirestoreViewModel(application: Application) : AndroidViewModel(application) {

    private val con = application.applicationContext
    private val firebaseRepository =
        FirestoreRepository(application)
    private var profile: MutableLiveData<List<ProfileEntity>> = MutableLiveData()
    private var item: MutableLiveData<List<ItemEntity>> = MutableLiveData()

    fun logOut() {
        firebaseRepository.logOut()
    }

    fun updateFirestore(
        profile: ProfileEntity
    ) {
        firebaseRepository.updateFirestore(
            profile
        ).addOnSuccessListener {
            Log.i(TAG, "Update completed.")
            Toast.makeText(con, con.resources.getString(R.string.synchro_good), Toast.LENGTH_SHORT)
                .show()
        }.addOnFailureListener { e ->
            Toast.makeText(con, con.resources.getString(R.string.synchro_bad), Toast.LENGTH_SHORT)
                .show()
            Log.e(TAG, "Error while updating", e)
        }
    }

    fun addDevice(
        profile: ProfileEntity
    ) {
        firebaseRepository.updateFirestore(
            profile
        ).addOnSuccessListener {
            Log.i(TAG, "Update completed.")
        }.addOnFailureListener { e ->
            Log.e(TAG, "Error while updating", e)
        }
    }

    // getting the old data - reinstalling app

    fun getProfile(): LiveData<List<ProfileEntity>> {
        firebaseRepository.getProfileFromFirestore().addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                profile.value = null
                return@addSnapshotListener
            }
            val temp: MutableList<ProfileEntity> = mutableListOf()
            if (snapshot != null && snapshot.exists()) {
                val item = snapshot.toObject(ProfileEntity::class.java)!!
                temp.add(item)
                Log.i(TAG, "Document snapshot: ${snapshot.data}")
            } else {
                Log.i(TAG, "Current data: null")
            }
            profile.value = temp
        }
        return profile
    }

    fun saveProfile(profileEntity: ProfileEntity) {
        firebaseRepository.saveProfileToFirebase(profileEntity)
            .addOnSuccessListener { Log.i(TAG, "Successfully add Profile to Database Firestore!") }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding document", e)
            }
    }

    fun saveItem(itemEntity: ItemEntity) {
        firebaseRepository.saveItemToFirebase(itemEntity)
            .addOnSuccessListener { Log.i(TAG, "Successfully add Device to Database Firestore!") }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding document", e)
            }
    }

    companion object {
        const val TAG = "FIRESTORE_VIEW_MODEL"
    }
}
