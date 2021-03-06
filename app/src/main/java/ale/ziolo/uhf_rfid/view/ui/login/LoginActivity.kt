package ale.ziolo.uhf_rfid.view.ui.login

import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.model.entities.ProfileEntity
import ale.ziolo.uhf_rfid.view.ui.synchronize.SynchronizeActivity
import ale.ziolo.uhf_rfid.view.ui.addDevice.AddDeviceActivity
import ale.ziolo.uhf_rfid.view.ui.main.MainActivity
import ale.ziolo.uhf_rfid.firestore.FirestoreViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private var state: Int = 0
    private var mEMAIL: String = ""
    private var mNAME: String = ""
    private var mPASSWORD: String = ""

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(
            LoginViewModel::class.java
        )
    }
    private val firestoreViewModel: FirestoreViewModel by lazy {
        ViewModelProviders.of(this).get(
            FirestoreViewModel::class.java
        )
    }

    // Configure Google Sign In
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val gso: GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("582425535306-2as38il829cvml3dfep4mkago7fsprdq.apps.googleusercontent.com")
            .requestEmail()
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setVisibility(state)
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()


        button_sign.setOnClickListener {
            //new user
            state = 1
            setVisibility(state)
        }

        button_log.setOnClickListener {
            //old user
            state = 2
            setVisibility(state)
        }

        button_back.setOnClickListener {
            state = 0
            setVisibility(state)
        }

        // method of log
        google_button.setOnClickListener {
            signInWithGoogle()
        }

        email_button.setOnClickListener {
            use_identifier_CV.isVisible = true
            if (state == 2) {
                input_name.isVisible = false
                image_name.isVisible = false
            } else {
                input_name.isVisible = true
                image_name.isVisible = true
            }
        }

        button_go_to_creation_profile.setOnClickListener {
            nextStepWithEmail()
        }
    }

    public override fun onStart() {
        super.onStart()
        //  Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, SIGN_IN_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.e("GOOGLE", "Google sign in failed", e)
                Log.e("GOOLGE", "Google sign in failed tutututu", e)
                Toast.makeText(
                    this,
                    resources.getString(R.string.auth_failed_early),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (state == 1) {
                        val email = account.email.toString()
                        val name = account.displayName.toString()
                        createAccount(name, email)
                    }
                    if (state == 2) {
                        val intent = Intent(this, SynchronizeActivity::class.java)
                        intent.putExtra("email", account.email.toString())
                        intent.putExtra("name", account.displayName.toString())
                        startActivity(intent)
                    }
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("GOOGLE", "signInWithCredential:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("GOOGLE", "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun createUserUsingEmailAndPassword() {
        auth.createUserWithEmailAndPassword(mEMAIL, mPASSWORD)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Sign in success, update UI with the signed-in user's information
                    Log.d("GOOGLE", "createUserWithEmail:success")
                    val name = mNAME
                    val email = mEMAIL
                    createAccount(name, email)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("GOOGLE", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        resources.getString(R.string.email_already_used),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun logInUsingEmailAuthorisation() {
        auth.signInWithEmailAndPassword(mEMAIL, mPASSWORD)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("GOOGLE", "signInWithEmail:success")
                    val intent = Intent(this, SynchronizeActivity::class.java)
                    intent.putExtra("email", mEMAIL)
                    intent.putExtra("name", mNAME)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("GOOGLE", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        resources.getString(R.string.bad_email),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    private fun setVisibility(current: Int) {
        if (current == 0) {
            // choose method
            startCV.isVisible = true
            loginCV.isVisible = false
            button_back.isVisible = false
            use_identifier_CV.isVisible = false
        }
        if (current == 1) {
            //new user
            startCV.isVisible = false
            loginCV.isVisible = true
            button_back.isVisible = true
            introduction.text = resources.getString(R.string.create_new_account)
        }
        if (current == 2) {
            //old user
            startCV.isVisible = false
            loginCV.isVisible = true
            button_back.isVisible = true
            introduction.text = resources.getString(R.string.log_in_start)
        }
    }

    private fun nextStepWithEmail() {
        if (state == 1) {
            //new user - create account
            if (input_email.text.isNotEmpty() && input_name.text.isNotEmpty() && input_password.text.isNotEmpty()) {
                if (input_password.text.length >= 6) {
                    mNAME = input_name.text.toString()
                    mEMAIL = input_email.text.toString()
                    mPASSWORD = input_password.text.toString()
                    createUserUsingEmailAndPassword()
                } else {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.password_length),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
            if (input_email.text.isEmpty() || input_name.text.isEmpty() || input_password.text.isEmpty()) {
                Toast.makeText(this, resources.getString(R.string.complete_all), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        if (state == 2) {
            //old user - login to old account
            if (input_email.text.isNotEmpty() && input_password.text.isNotEmpty()) {
                mEMAIL = input_email.text.toString()
                mPASSWORD = input_password.text.toString()
                logInUsingEmailAuthorisation()
            }
            if (input_email.text.isEmpty() || input_password.text.isEmpty()) {
                Toast.makeText(this, resources.getString(R.string.complete_all), Toast.LENGTH_SHORT)
                    .show()
            }
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

        loginViewModel.insertProfile(profile, "start")
        firestoreViewModel.saveProfile(profile)
    }

    private fun createAccount(name: String, email: String){
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

    companion object {
        const val SIGN_IN_REQUEST: Int = 1
    }
}