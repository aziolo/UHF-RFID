package ale.ziolo.uhf_rfid.view.ui.items

import ale.ziolo.uhf_rfid.databinding.FragmentItemsBinding
import ale.ziolo.uhf_rfid.view.AddDeviceActivity
import ale.ziolo.uhf_rfid.view.AddItemActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_items.*

class ItemsFragment : Fragment() {

  private lateinit var itemsViewModel: ItemsViewModel
private var _binding: FragmentItemsBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    itemsViewModel =
            ViewModelProvider(this).get(ItemsViewModel::class.java)

    _binding = FragmentItemsBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textIte
    itemsViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })


    binding.button2.setOnClickListener {

      val intent = Intent(context, AddDeviceActivity::class.java)
      intent.putExtra("state", "next")
      startActivity(intent)
    }

    binding.button3.setOnClickListener {

      val intent = Intent(context, AddItemActivity::class.java)
      intent.putExtra("state", "next")
      startActivity(intent)
    }

    binding.button4.setOnClickListener {

    }



    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}