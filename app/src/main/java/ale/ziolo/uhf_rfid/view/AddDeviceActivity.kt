package ale.ziolo.uhf_rfid.view

import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.model.entities.DeviceEntity
import ale.ziolo.uhf_rfid.viewModels.DeviceViewModel
import ale.ziolo.uhf_rfid.viewModels.FirestoreViewModel
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

    private lateinit var tag: String
    private lateinit var state_tag: String

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

        val intent = intent
        tag = intent.getStringExtra("tag").toString()
        state_tag = intent.getStringExtra("state_tag").toString()


        if (state_tag == "add_device" && tag.isNotEmpty()){
            input_identifier.text = Editable.Factory.getInstance()
                .newEditable(tag)
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
            if (input_identifier.text.length == 24) {
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