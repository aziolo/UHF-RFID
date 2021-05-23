package ale.ziolo.uhf_rfid.view.ui.addItem

import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.databinding.ActivityAddItemBinding
import ale.ziolo.uhf_rfid.databinding.ActivityAddRuleBinding
import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.view.ui.scannerQR.ScannerQRActivity
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
import kotlinx.android.synthetic.main.activity_add_item.*
import javax.inject.Inject

class AddItemActivity : AppCompatActivity() {

    private lateinit var tag: String
    private lateinit var stateTag: String
    private lateinit var binding: ActivityAddItemBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val addItemViewModel: AddItemViewModel by lazy {
        ViewModelProviders.of(this).get(
            AddItemViewModel::class.java
        )
    }
    private val firestoreViewModel: FirestoreViewModel by lazy {
        ViewModelProviders.of(this).get(
            FirestoreViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        tag = intent.getStringExtra("tag").toString()
        stateTag = intent.getStringExtra("state_tag").toString()


        if (stateTag == "add_item" && tag.isNotEmpty()) {
            input_identifier_item.text = Editable.Factory.getInstance()
                .newEditable(tag)
        }

        binding.buttonUseQrItem.setOnClickListener {
            //new activity qr
            val intent = Intent(this, ScannerQRActivity::class.java)
            intent.putExtra("state", "item")
            startActivity(intent)
        }


        binding.buttonAddItem.setOnClickListener {
            check()
        }

        binding.buttonSkipItem.setOnClickListener {
            //new activity qr
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addItem(name: String, taggi: String) {
        val item = ItemEntity(
            taggi,
            name
        )
        addItemViewModel.insert(item)
        firestoreViewModel.saveItem(item)
        val intent1 = Intent(this, MainActivity::class.java)
        startActivity(intent1)
    }


    private fun check() {
        if (binding.inputIdentifierName.text.isNotEmpty() && binding.inputIdentifierItem.text.isNotEmpty()) {
            if (binding.inputIdentifierItem.text.length == 24) {
                try {
                    addItem(binding.inputIdentifierName.text.toString(), binding.inputIdentifierItem.text.toString())
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
        if (binding.inputIdentifierName.text.isEmpty() || binding.inputIdentifierItem.text.isEmpty()) {
            Toast.makeText(this, resources.getString(R.string.complete_all), Toast.LENGTH_SHORT)
                .show()
        }
    }

}
