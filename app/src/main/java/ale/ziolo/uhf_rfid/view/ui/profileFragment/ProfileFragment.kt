package ale.ziolo.uhf_rfid.view.ui.profileFragment
import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.databinding.FragmentProfileBinding
import ale.ziolo.uhf_rfid.view.ui.addDevice.AddDeviceActivity
import ale.ziolo.uhf_rfid.view.ManageAccountActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this).get(
            ProfileViewModel::class.java
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        fillValues()

        binding.buttonEditProfile.setOnClickListener {
            val intent = Intent(context, ManageAccountActivity::class.java)
            startActivity(intent)
        }
        binding.buttonChangeDevice.setOnClickListener {
            val intent = Intent(context, AddDeviceActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillValues() {
        val profile = profileViewModel.getProfile()
        binding.prName.text = profile.name
        binding.prEmail.text = profile.email
        if (profile.device_id.isNotEmpty()) binding.prDevice.text = profile.device_id
        else binding.prDevice.text = resources.getString(R.string.no_device)
    }
}