package ale.ziolo.uhf_rfid.repositories

import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.model.entities.RuleEntity
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
    private val itemCollectionReference = firestoreDB.collection("users")
        .document(user.uid)
        .collection("items")
    private val ruleCollectionReference = firestoreDB.collection("users")
        .document(user.uid)
        .collection("rules")



    fun logOut() {
        auth.signOut()
    }

    fun getProfileFromFirestore(): DocumentReference {
        return profileDocumentReference
    }

    fun getAllItemsFromFirestore(): CollectionReference {
        return itemCollectionReference
    }

    fun getAllRulesFromFirestore(): CollectionReference {
        return ruleCollectionReference
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

    fun saveItemToFirebase(
        item: ItemEntity
    ): Task<Transaction> {
        return firestoreDB.runTransaction { transaction ->
            transaction.set(itemCollectionReference.document(item.tag), item)
        }
    }

    fun saveRuleToFirebase(
        rule: RuleEntity
    ): Task<Transaction> {
        return firestoreDB.runTransaction { transaction ->
            transaction.set(ruleCollectionReference.document(rule.id.toString()), rule)
        }
    }

}

