package ale.ziolo.uhf_rfid.view

import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.viewModels.FirestoreViewModel
import ale.ziolo.uhf_rfid.viewModels.ProfileViewModel
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_manage_account.*
import java.util.*
import javax.inject.Inject

class ManageAccountActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_manage_account)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        state = intent.getStringExtra("state").toString()

        if (state == "edit") {
            mEmail = user.email!!
            mName = user.displayName!!
            fillOldValues()
            input_name_acc.text = Editable.Factory.getInstance()
                .newEditable(mName)
            input_email.text = Editable.Factory.getInstance()
                .newEditable(mEmail)
        }

        button_save_account.setOnClickListener {
            checkAccount()
        }

    }
    public override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        state = intent.getStringExtra("state").toString()
        setStatus()
    }

    private fun setStatus(){
        if (state == "create_email" || state == "log") {
            mEmail = intent.getStringExtra("email").toString()
            mName = intent.getStringExtra("name").toString()
            createAccount()

        }
        if (state == "create_google") {
            mEmail = user.email!!
            mName = user.displayName!!
            createAccount()
        }

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

            if (state == "edit") {
                try {
                    updateProfile(
                        input_name_acc.text.toString()
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
        email: String
    ) {
        val uuid: UUID = UUID.randomUUID()
        val id1: Long = uuid.mostSignificantBits
        val id2: Long = uuid.leastSignificantBits
        val profile = ProfileEntity(
            id1,
            id2,
            name,
            email
        )
        profileViewModel.insertProfile(profile)
        firestoreViewModel.saveProfile(profile)
    }

    private fun updateProfile(
        name: String
    ) {
        val old = profileViewModel.getOneProfile()
        val updated = ProfileEntity(
            old.id1,
            old.id2,
            name,
            old.email
        )
        profileViewModel.updateProfile(updated)
    }

    private fun createAccount(){
            try {
                createProfileEntity(
                    mName,
                    mEmail,
                )
                Toast.makeText(
                    this,
                    resources.getString(R.string.profile_saved),
                    Toast.LENGTH_SHORT
                ).show()
                val intent1 = Intent(this, AddDeviceActivity::class.java)
                startActivity(intent1)
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.try_again),
                    Toast.LENGTH_SHORT
                ).show()
            }

    }

}