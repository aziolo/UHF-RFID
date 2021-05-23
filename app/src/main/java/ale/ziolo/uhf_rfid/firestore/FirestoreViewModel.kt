package ale.ziolo.uhf_rfid.firestore

import ale.ziolo.uhf_rfid.repositories.FirestoreRepository
import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.model.entities.RuleEntity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot

class FirestoreViewModel(application: Application) : AndroidViewModel(application) {

    private val con = application.applicationContext
    private val firebaseRepository =
        FirestoreRepository(application)
    private var profile: MutableLiveData<List<ProfileEntity>> = MutableLiveData()
    private var savedItemsList: MutableLiveData<List<ItemEntity>> = MutableLiveData()
    private var savedRulesList: MutableLiveData<List<RuleEntity>> = MutableLiveData()

    fun logOut() {
        firebaseRepository.logOut()
    }

    fun updateDevice(
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
    // getting the old data - reinstalling app
    fun getAllItems(): LiveData<List<ItemEntity>> {
        firebaseRepository.getAllItemsFromFirestore()
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    savedItemsList.value = null
                    return@EventListener
                }

                val temporaryItemsList: MutableList<ItemEntity> = mutableListOf()
                for (doc in value!!) {
                    val item = doc.toObject(ItemEntity::class.java)
                    temporaryItemsList.add(item)
                }
                savedItemsList.value = temporaryItemsList
            })

        return savedItemsList
    }

    fun getAllRules(): LiveData<List<RuleEntity>> {
        firebaseRepository.getAllRulesFromFirestore()
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    savedRulesList.value = null
                    return@EventListener
                }

                val temporaryList: MutableList<RuleEntity> = mutableListOf()
                for (doc in value!!) {
                    val item = doc.toObject(RuleEntity::class.java)
                    temporaryList.add(item)
                }
                savedRulesList.value = temporaryList
            })
        return savedRulesList
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

    fun saveRule(ruleEntity: RuleEntity) {
        firebaseRepository.saveRuleToFirebase(ruleEntity)
            .addOnSuccessListener { Log.i(TAG, "Successfully add rule to Database Firestore!") }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding document", e)
            }
    }
    fun deleteItem(itemEntity: ItemEntity) {
        firebaseRepository.deleteItemFromFirebase(itemEntity)
            .addOnSuccessListener { Log.i(TAG, "Successfully deleted item from firebase!") }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error deleting document", e)
            }
    }
    fun deleteRule(ruleEntity: RuleEntity) {
        firebaseRepository.deleteRuleFromFirebase(ruleEntity)
            .addOnSuccessListener { Log.i(TAG, "Successfully deleted rule from firebase!") }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error deleting document", e)
            }
    }

    companion object {
        const val TAG = "FIRESTORE_VIEW_MODEL"
    }
}
