package ale.ziolo.uhf_rfid.view.ui.synchronize

import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.model.entities.RuleEntity
import ale.ziolo.uhf_rfid.view.ui.addDevice.AddDeviceActivity
import ale.ziolo.uhf_rfid.view.ui.main.MainActivity
import ale.ziolo.uhf_rfid.viewModels.FirestoreViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

class SynchronizeActivity : AppCompatActivity() {

    private lateinit var cloudItemsList: List<ItemEntity>
    private lateinit var cloudRulesList: List<RuleEntity>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val synchronizeViewModel: SynchronizeViewModel by lazy {
        ViewModelProviders.of(this).get(
            SynchronizeViewModel::class.java
        )
    }
    private val firestoreViewModel: FirestoreViewModel by lazy {
        ViewModelProviders.of(this).get(
            FirestoreViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_synchronize)
        showDialog()

    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.synchronize))
        builder.setPositiveButton("TAK") { _, _ ->
            download()
        }
        builder.setNegativeButton("NIE") { _, _ ->
            val name = intent.getStringExtra("name")!!
            val email = intent.getStringExtra("email")!!
            createProfile(name, email)

        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun createProfile(name: String, email: String){
        try {
            createProfileEntity(
                name,
                email
            )
            Toast.makeText(
                this,
                resources.getString(R.string.profile_saved),
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, AddDeviceActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                resources.getString(R.string.try_again),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun createProfileEntity(
        name: String,
        email: String
    ) {
        val profile = ProfileEntity(
            name,
            email,
            "",
            ""
        )
        synchronizeViewModel.insertProfile(profile)
        firestoreViewModel.saveProfile(profile)
    }

    private fun download() {
        try {
            firestoreViewModel.getProfile().observe(this, Observer {
                val profile = it[0]
                synchronizeViewModel.insertProfile(profile)
            })
            firestoreViewModel.getAllItems().observe(this, Observer {
                cloudItemsList = it
                for (i in cloudItemsList.indices) {
                    synchronizeViewModel.insertItem(cloudItemsList[i])
                }
            })
            firestoreViewModel.getAllRules().observe(this, Observer {
                cloudRulesList = it
                for (i in cloudRulesList.indices) {
                    synchronizeViewModel.insertRule(cloudRulesList[i])
                }
            })
        } catch (e: Exception){
            Log.w("TAG", "download old data failed",e)
            Toast.makeText(
                baseContext,
                resources.getString(R.string.failed_update),
                Toast.LENGTH_SHORT
            ).show()
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}