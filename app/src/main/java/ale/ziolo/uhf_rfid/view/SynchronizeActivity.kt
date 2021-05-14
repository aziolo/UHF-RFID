package ale.ziolo.uhf_rfid.view

import ale.ziolo.uhf_rfid.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class SynchronizeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_synchronize)
        val mode = intent.getStringExtra("mode")
        if (mode == "download") {
            // WHEN OLD USER WANT TO DOWNLOAD OLD DATA
            showDialog()
        }
        if (mode == "updateCloud") {
            updateCloud()
        }
    }

    private fun updateCloud() {
        // TO DO
    }

    private fun download() {
        // TO DO
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.synchronize))
        builder.setPositiveButton("TAK") { _, _ ->
            download()
        }
        builder.setNegativeButton("NIE") { _, _ ->
            val name = intent.getStringExtra("name")
            val email = intent.getStringExtra("email")
            val intent = Intent(this, ManageAccountActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("name", name)
            intent.putExtra("state", "log")
            startActivity(intent)

        }
        val dialog = builder.create()
        dialog.show()
    }


}