package ale.ziolo.uhf_rfid.view.ui.addDevice

import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.view.ui.scannerQR.ScannerQRActivity
import ale.ziolo.uhf_rfid.view.ui.addItem.AddItemActivity
import ale.ziolo.uhf_rfid.view.ui.main.MainActivity
import ale.ziolo.uhf_rfid.firestore.FirestoreViewModel
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
    private lateinit var stateTag: String

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val addDeviceViewModel: AddDeviceViewModel by lazy {
        ViewModelProviders.of(this).get(
            AddDeviceViewModel::class.java
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
        stateTag = intent.getStringExtra("state_tag").toString()


        if (stateTag == "add_device" && deviceId.isNotEmpty()){
            input_identifier.text = Editable.Factory.getInstance()
                .newEditable(deviceId)
        }

        button_use_qr.setOnClickListener {
            //new activity qr
            val intent2 = Intent(this, ScannerQRActivity::class.java)
            intent2.putExtra("state","device")
            startActivity(intent2)
        }

        button_add_item.setOnClickListener {
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
            val old = addDeviceViewModel.getProfile()
            val newDevice = input_identifier.text.toString()
            val updated = ProfileEntity(
                old.name,
                old.email,
                newDevice,
                ""
            )
            addDeviceViewModel.updateProfile(updated)
            firestoreViewModel.updateDevice(updated)
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
                addDevice()
            }
        }
        if (input_identifier.text.isEmpty() ) {
            Toast.makeText(this, resources.getString(R.string.complete_all), Toast.LENGTH_SHORT)
                .show()
        }
    }


}