package ale.ziolo.uhf_rfid.view

import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.model.entities.DeviceEntity
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.viewModels.DeviceViewModel
import ale.ziolo.uhf_rfid.viewModels.FirestoreViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add_device.*
import kotlinx.android.synthetic.main.activity_add_device.introduction
import java.util.*
import javax.inject.Inject


class AddDeviceActivity : AppCompatActivity() {

    private var state: Int = 0
    private lateinit var tag: String
    private lateinit var state2: String

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val deviceViewModel: DeviceViewModel by lazy {
        ViewModelProviders.of(this).get(
            DeviceViewModel::class.java
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

        setVisibility(state)
        val intent = intent
        tag = intent.getStringExtra("tag").toString()
        state2 = intent.getStringExtra("state").toString()

        if (state2 == "add_device" && tag.isNotEmpty()){
            setVisibility(1)
            input_identifier.text = Editable.Factory.getInstance()
                .newEditable(tag)
        }

        button_use_qr.setOnClickListener {
            //new activity qr
            state = 0
            setVisibility(state)
            val intent2 = Intent(this, ScannerQRActivity::class.java)
            startActivity(intent2)
        }

        button_use_text.setOnClickListener {
            //old user
            state = 1
            setVisibility(state)
        }
        button_add_device.setOnClickListener {
            addDevice()
        }
    }

    private fun addDevice() {
        try {
            val device = DeviceEntity(
                tag
            )
            deviceViewModel.insert(device)
            firestoreViewModel.saveDevice(device)
            Toast.makeText(
                this,
                resources.getString(R.string.device_saved),
                Toast.LENGTH_SHORT
            ).show()
            val intent1 = Intent(this,MainActivity::class.java)
            startActivity(intent1)

        } catch (e: Exception) {
            Toast.makeText(
                this,
                resources.getString(R.string.try_again),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setVisibility(current: Int) {
        if (current == 0) {
            // choose method
            use_identifier_CV.isVisible = false
        }
        if (current == 1) {
            use_identifier_CV.isVisible = true
        }

    }
}