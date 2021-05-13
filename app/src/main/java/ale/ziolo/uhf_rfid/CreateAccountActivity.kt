package ale.ziolo.uhf_rfid

import ale.ziolo.uhf_rfid.data.ProfileEntity
import ale.ziolo.uhf_rfid.vieModels.FirestoreViewModel
import ale.ziolo.uhf_rfid.vieModels.ProfileViewModel
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_create_account.*
import java.util.*
import javax.inject.Inject

class CreateAccountActivity : AppCompatActivity() {

    private var ruleType: Int = 0
    private lateinit var mEmail: String
    private lateinit var mName: String
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var state: String

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this).get(
            ProfileViewModel::class.java
        )
    }
    private val firestoreViewModel: FirestoreViewModel by lazy {
        ViewModelProviders.of(this).get(
            FirestoreViewModel::class.java
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        state = intent.getStringExtra("state").toString()

        if (state == "edit") {
            mEmail = user.email!!
            mName = user.displayName!!
            fillOldValues()
        }
        if (state == "create_email" || state == "log") {
            mEmail = intent.getStringExtra("email").toString()
            mName = intent.getStringExtra("name").toString()

        }
        if (state == "create_google") {
            mEmail = user.email!!
            mName = user.displayName!!
        }

        button_save_account.setOnClickListener {
            checkAccount()
        }
        input_name_acc.text = Editable.Factory.getInstance()
            .newEditable(mName)
        input_email.text = Editable.Factory.getInstance()
            .newEditable(mEmail)

    }

    private fun fillOldValues() {
        val profile = profileViewModel.getOneProfile()
        input_name_acc.text = Editable.Factory.getInstance()
            .newEditable(profile.name)
        input_email.text = Editable.Factory.getInstance()
            .newEditable(profile.email)

    }
    private fun checkAccount() {
        if (input_name_acc.text.isNotEmpty() && input_email.text.isNotEmpty() && input_device.text.isNotEmpty()) {

            if (state == "create_google" || state == "create_email" || state == "log") {
                try {
                    createProfileEntity(
                        input_name_acc.text.toString(),
                        input_email.text.toString(),
                        input_device.text.toString(),
                    )
                    Toast.makeText(
                        this,
                        resources.getString(R.string.profile_saved),
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.try_again),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            if (state == "edit") {
                try {
                    updateProfile(
                        input_name_acc.text.toString(),
                        input_device.text.toString()
                    )
                    Toast.makeText(
                        this,
                        resources.getString(R.string.profile_updated),
                        Toast.LENGTH_SHORT
                    ).show()
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.try_again),
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }

        }
        if (input_name_acc.text.isEmpty() || input_email.text.isEmpty() || input_device.text.isEmpty()) {
            Toast.makeText(this, resources.getString(R.string.complete_all), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun createProfileEntity(
        name: String,
        email: String,
        deviceID: String
    ) {
        val uuid: UUID = UUID.randomUUID()
        val id1: Long = uuid.mostSignificantBits
        val id2: Long = uuid.leastSignificantBits
        val profile = ProfileEntity(
            id1,
            id2,
            name,
            email,
            deviceID
        )
        profileViewModel.insertProfile(profile)
        firestoreViewModel.saveProfile(profile)
    }

    private fun updateProfile(
        name: String,
        deviceID: String
    ) {
        val old = profileViewModel.getOneProfile()
        val updated = ProfileEntity(
            old.id1,
            old.id2,
            name,
            old.email,
            deviceID
        )
        profileViewModel.updateProfile(updated)
    }

}