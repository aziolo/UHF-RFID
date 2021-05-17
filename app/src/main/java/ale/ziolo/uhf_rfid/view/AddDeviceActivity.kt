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
import kotlinx.android.synthetic.main.activity_add_device.*
import javax.inject.Inject


class AddDeviceActivity : AppCompatActivity() {

    private lateinit var deviceId: String
    private lateinit var state_tag: String

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
        setContentView(R.layout.activity_add_device)

        val intent = intent
        deviceId = intent.getStringExtra("tag").toString()
        state_tag = intent.getStringExtra("state_tag").toString()


        if (state_tag == "add_device" && deviceId.isNotEmpty()){
            input_identifier.text = Editable.Factory.getInstance()
                .newEditable(deviceId)
        }

        button_use_qr.setOnClickListener {
            //new activity qr
            val intent2 = Intent(this, ScannerQRActivity::class.java)
            intent2.putExtra("state","device")
            startActivity(intent2)
        }

        button_add_device.setOnClickListener {
            check()
        }

        button_skip_device.setOnClickListener {
            //new activity qr
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addDevice() {
        try {
            val old = profileViewModel.getOneProfile()
            val updated = ProfileEntity(
                old.name,
                old.email,
                deviceId
            )
            profileViewModel.updateProfile(updated)
            firestoreViewModel.addDevice(updated)
            Toast.makeText(
                this,
                resources.getString(R.string.device_saved),
                Toast.LENGTH_SHORT
            ).show()
                val intent1 = Intent(this, AddItemActivity::class.java)
                startActivity(intent1)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                resources.getString(R.string.try_again),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun check() {
        if (input_identifier.text.isNotEmpty()) {
            if (input_identifier.text.length == 20) {
                try {
                    addDevice()
                    Toast.makeText(
                        this,
                        resources.getString(R.string.item_saved),
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
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.bad_id),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        if (input_identifier.text.isEmpty() ) {
            Toast.makeText(this, resources.getString(R.string.complete_all), Toast.LENGTH_SHORT)
                .show()
        }
    }


}