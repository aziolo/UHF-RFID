package ale.ziolo.uhf_rfid.view.ui.main

import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.databinding.ActivityMainBinding
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.viewModels.FirestoreViewModel
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val topic = "reminder"
    private lateinit var token: String
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(
            MainViewModel::class.java
        )
    }
    private val firestoreViewModel: FirestoreViewModel by lazy {
        ViewModelProviders.of(this).get(
            FirestoreViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        createChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name)
        )
        subscribeTopic()
    }

    override fun onStart() {
        super.onStart()
        getToken()
        addToken()
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    setShowBadge(false)
                }
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.notification_channel_description)

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }
    }

    private fun subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                var message = getString(R.string.message_subscribed)
                if (!task.isSuccessful) {
                    message = getString(R.string.message_subscribe_failed)
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun getToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("GOOGLE", "Fetching FCM registration token failed", task.exception)
                token =resources.getString(R.string.google_error)
                Toast.makeText(
                    this,
                    resources.getString(R.string.google_error),
                    Toast.LENGTH_LONG
                ).show()
                return@OnCompleteListener
            }
            // Get new FCM registration token
            token = task.result
            Log.e("GOOLGE", token)
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })
    }
    private fun addToken() {
        try {
            val old = mainViewModel.getProfile()
            val updated = ProfileEntity(
                old.name,
                old.email,
                old.device_id,
                token
            )
            mainViewModel.updateProfile(updated)
            firestoreViewModel.updateDevice(updated)

        } catch (e: Exception) {
        }
    }

}