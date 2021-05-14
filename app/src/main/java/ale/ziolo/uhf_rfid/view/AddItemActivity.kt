package ale.ziolo.uhf_rfid.view

import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.model.entities.DeviceEntity
import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.viewModels.FirestoreViewModel
import ale.ziolo.uhf_rfid.viewModels.ItemViewModel
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class AddItemActivity : AppCompatActivity() {

    private lateinit var tag: String
    private lateinit var state_tag: String

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val itemViewModel: ItemViewModel by lazy {
        ViewModelProviders.of(this).get(
            ItemViewModel::class.java
        )
    }
    private val firestoreViewModel: FirestoreViewModel by lazy {
        ViewModelProviders.of(this).get(
            FirestoreViewModel::class.java
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val intent = intent
        tag = intent.getStringExtra("tag").toString()
        state_tag = intent.getStringExtra("state_tag").toString()


        if (state_tag == "add_item" && tag.isNotEmpty()){
            input_identifier_item.text = Editable.Factory.getInstance()
                .newEditable(tag)
        }

        button_use_qr_item.setOnClickListener {
            //new activity qr
            val intent2 = Intent(this, ScannerQRActivity::class.java)
            intent2.putExtra("state","item")
            startActivity(intent2)
        }

        button_add_item.setOnClickListener {
            check()
        }
    }

    private fun addDevice(name: String) {
        try {
            val item = ItemEntity(
                tag,
                name
            )
            itemViewModel.insert(item)
            firestoreViewModel.saveItem(item)
            Toast.makeText(
                this,
                resources.getString(R.string.item_saved),
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


    private fun check() {
        if (input_identifier_name.text.isNotEmpty() && input_identifier_item.text.isNotEmpty()) {
            if (input_identifier_item.text.length == 24) {
                try {
                    addDevice(input_identifier_name.text.toString())
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
        if (input_identifier_name.text.isEmpty() || input_identifier_item.text.isEmpty()) {
            Toast.makeText(this, resources.getString(R.string.complete_all), Toast.LENGTH_SHORT)
                .show()
        }
    }

}
