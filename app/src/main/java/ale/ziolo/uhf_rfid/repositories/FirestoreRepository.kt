package ale.ziolo.uhf_rfid.repositories

import ale.ziolo.uhf_rfid.model.entities.DeviceEntity
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import android.app.Application
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Transaction

class FirestoreRepository(val application: Application) {

    private var firestoreDB = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val user: FirebaseUser = auth.currentUser!!

    private val profileDocumentReference = firestoreDB.collection("users")
        .document(user.uid)

    private val deviceCollectionReference = firestoreDB.collection("users")
        .document(user.uid)
        .collection("devices")

    fun logOut() {
        auth.signOut()
    }

    fun getProfileFromFirestore(): DocumentReference {
        return profileDocumentReference
    }

    fun saveProfileToFirebase(profileEntity: ProfileEntity): Task<Void> {
        return profileDocumentReference.set(profileEntity)
    }

    fun updateFirestore(
        profile: ProfileEntity
    ): Task<Transaction> {
        return firestoreDB.runTransaction { transaction ->
            //setting new values
            transaction.set(profileDocumentReference, profile)
        }
    }
    fun saveDeviceToFirebase(
        device: DeviceEntity
    ): Task<Transaction> {
        return firestoreDB.runTransaction { transaction ->
            transaction.set(deviceCollectionReference.document(device.tag), device)
            }
        }

}

